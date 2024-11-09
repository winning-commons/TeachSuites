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

export const getStudentClasses = async (
  req: Request,
  res: Response
): Promise<void> => {
  const student_id = req.body["student-id"];

  // Validate the student_id to ensure it's a UUID
  if (!isUUID(student_id)) {
    res.status(400).json({ error: "Invalid UUID format for student-id" });
    return; // Exit the function to prevent further execution
  }

  try {
    const query = `
      SELECT classroom_id FROM classroom_students
      WHERE student_id = $1;
    `;
    const values = [student_id];

    const result = await pool.query(query, values);

    res.json(result.rows); // Return all classroom_ids associated with the student_id
  } catch (error) {
    console.error("Error fetching data from the database:", error);
    res.status(500).json({ error: "Failed to retrieve classrooms" });
  }
};
