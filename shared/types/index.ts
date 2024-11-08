export interface User {
  id: string;
  name: string;
  email: string;
  role: "teacher" | "student";
}

export interface Question {
  id: string;
  content: string;
  type: "multiple_choice" | "short_answer";
  correctAnswer: string;
}

export interface Exam {
  id: string;
  title: string;
  questions: Question[];
  duration: number;
  startTime: Date;
}
