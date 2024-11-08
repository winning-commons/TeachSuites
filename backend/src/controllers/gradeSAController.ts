import { Request, Response } from 'express';
const { GoogleGenerativeAI } = require("@google/generative-ai");


export const gradeSAInput = async (req: Request, res: Response): Promise<void> => {
  const input = req.body['sa-input'];
  const question = req.body['sa-question'];
  var grade_sa_prompt = process.env.GRADE_SA_PROMPT;
  grade_sa_prompt += "Question: " + question + "\nAnswer: " + input;
//   console.log('Received SA input:', input);
//   console.log('Secret Key:', process.env.GEMINI_KEY);

  const genAI = new GoogleGenerativeAI(process.env.GEMINI_KEY);
  const model = genAI.getGenerativeModel({ model: "gemini-1.5-flash" });

  
  const result = await model.generateContent([grade_sa_prompt]);
//   console.log(result.response);
    var responseArray = result.response.text().split("\n").filter((str: string) => str.trim() !== "");
    const result_json = {
        points: responseArray[0].replace(/^\d+\.\s*/, ''), // Removes '1. '
        feedback: responseArray[1].replace(/^\d+\.\s*/, ''), // Removes '2. '
        improvements: responseArray[2].replace(/^\d+\.\s*/, ''), // Removes '3. '
        sources: responseArray[3].replace(/^\d+\.\s*/, '') // Removes '4. '
      };
      



  res.json(result_json);
};
