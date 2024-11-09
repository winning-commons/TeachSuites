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

export const getExamQs = async (
    req: Request,
    res: Response
): Promise<void> => {
    const exam_id = req.body["exam-id"];

    // Validate the exam_id to ensure it's a UUID
    if (!isUUID(exam_id)) {
        res.status(400).json({ error: "Invalid UUID format for exam-id" });
        return;
    }

    try {
        // Query to get all rows from exam_questions with the matching exam_id
        const query = `
            SELECT *
            FROM exam_questions
            WHERE exam_id = $1;
        `;
        const result = await pool.query(query, [exam_id]);

        if (result.rows.length === 0) {
            res.status(404).json({ message: "No questions found for the given exam ID." });
            return;
        }

        res.json(result.rows); // Return all exam questions associated with the exam_id
    } catch (error) {
        console.error("Error fetching exam questions from the database:", error);
        res.status(500).json({ error: "Failed to retrieve exam questions" });
    }
};
