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
      SELECT classrooms.* 
      FROM classroom_students
      JOIN classrooms ON classroom_students.classroom_id = classrooms.id
      WHERE classroom_students.student_id = $1;
    `;
        const values = [student_id];

        const result = await pool.query(query, values);

        res.json(result.rows); // Return detailed classroom information associated with the student_id
    } catch (error) {
        console.error("Error fetching data from the database:", error);
        res.status(500).json({ error: "Failed to retrieve classrooms" });
    }
};
