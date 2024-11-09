// src/controllers/getExamsController.ts
import { Request, Response } from "express";
import { Pool } from "pg";
import { validate as isUUID } from "uuid";
import dotenv from "dotenv";

dotenv.config();

const pool = new Pool({
  host: process.env.DB_HOST,
  port: parseInt(process.env.DB_PORT || "5432"),
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
  ssl: {
    rejectUnauthorized: false,
  },
});

export const getExams = async (
  req: Request,
  res: Response
): Promise<void> => {
  const student_id = req.body["student-id"];

  // Validate the student_id to ensure it's a UUID
  if (!isUUID(student_id)) {
    res.status(400).json({ error: "Invalid UUID format for student-id" });
    return;
  }

  try {
    // Query to get classroom IDs where the student is enrolled
    const classroomQuery = `
      SELECT classroom_id
      FROM classroom_students
      WHERE student_id = $1;
    `;
    const classroomResult = await pool.query(classroomQuery, [student_id]);
    const classroomIds = classroomResult.rows.map((row) => row.classroom_id);

    if (classroomIds.length === 0) {
      res.status(404).json({ message: "No exams found for the given student ID." });
      return;
    }

    // Query to get exams based on the classroom IDs
    const examsQuery = `
      SELECT *
      FROM exams
      WHERE classroom_id = ANY($1::uuid[]);
    `;
    const examsResult = await pool.query(examsQuery, [classroomIds]);

    res.json(examsResult.rows); // Return all exams associated with the classroom IDs
  } catch (error) {
    console.error("Error fetching exams from the database:", error);
    res.status(500).json({ error: "Failed to retrieve exams" });
  }
};
