import { Request, Response } from "express";
import { Pool } from "pg";
import { v4 as uuidv4, validate as isUUID } from "uuid";
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

export const makeClass = async (req: Request, res: Response): Promise<void> => {
  const classroom_id = req.body["google-classroom-id"];
  const teacher_id = req.body["teacher-id"];
  const class_name = req.body["class-name"];
  const description = req.body["class-description"];

  // Validate the teacher_id to ensure it's a UUID
  if (!isUUID(teacher_id)) {
    res.status(400).json({ error: "Invalid UUID format for teacher-id" });
    return; // Exit the function to prevent further execution
  }

  try {
    const id = uuidv4();

    const query = `
      INSERT INTO classrooms (id, teacher_id, name, google_classroom_id, description, created_at, updated_at)
      VALUES ($1, $2, $3, $4, $5, NOW(), NOW())
      RETURNING *;
    `;
    const values = [id, teacher_id, class_name, classroom_id, description];

    const result = await pool.query(query, values);

    res.json(result.rows[0]);
  } catch (error) {
    console.error("Error inserting data into the database:", error);
    res.status(500).json({ error: "Failed to create classroom" });
  }
};
