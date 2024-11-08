import { Request, Response } from "express";
const { GoogleGenerativeAI } = require("@google/generative-ai");

export const createMC = async (req: Request, res: Response): Promise<void> => {
  const topic = req.body["sa-topic"];
  if (topic === undefined) {
    res.status(400).json({ error: "Missing topic" });
    return;
  } else {
    const create_mc_prompt = process.env.CREATE_MC_PROMPT + topic;

    const genAI = new GoogleGenerativeAI(process.env.GEMINI_KEY);
    const model = genAI.getGenerativeModel({ model: "gemini-1.5-flash" });
    const result = await model.generateContent([create_mc_prompt]);
    //   console.log(result.response);
    var responseArray = result.response.text().split("\n");

    // turn responseArray into a JSON object
    console.log(responseArray);

    const responseObject = {
      question: responseArray[0].replace(/^\d+\.\s*/, ""), // Removes '1. '
      a: responseArray[1].replace(/^\d+\.\s*/, ""), // Removes '2. '
      b: responseArray[2].replace(/^\d+\.\s*/, ""), // Removes '3. '
      c: responseArray[3].replace(/^\d+\.\s*/, ""), // Removes '4. '
      d: responseArray[4].replace(/^\d+\.\s*/, ""), // Removes '4. '
    };
    res.json(responseObject);
  }
};
