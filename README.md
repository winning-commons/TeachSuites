## Inspiration
Teachers spend 40-50% of their time creating and grading assignments - time that could be better spent interacting with students and improving lesson plans. After speaking with educators, we discovered that while Google Classroom helps manage classes, it lacks robust assessment tools. This inspired us to create TeachSuites to automate and streamline the examination process.

## What it does
TeachSuites is an AI-powered assessment platform that integrates with Google Classroom. Teachers can easily create multiple-choice and short-answer questions, manage classrooms, and automatically grade responses. The platform uses Gemini AI to grade short answers intelligently, while providing an intuitive interface for building and organizing question banks. Students can take exams and receive instant feedback, while teachers save hundreds of hours annually on assessment tasks.

## How we built it
We developed native mobile apps using Kotlin (Android) and Swift (iOS coming soon), with a React.js web version planned. The backend uses Express.js/TypeScript with a PostgreSQL database. We implemented Google Classroom API integration for seamless class management and grade syncing. The UI is built with Jetpack Compose and Material 3 design, focusing on a clean, intuitive experience.

## Challenges we ran into
- Implementing reliable AI grading for short answers while maintaining accuracy
- Creating a flexible yet structured question builder that supports different question types
- Designing a database schema that efficiently handles various classroom and exam relationships
- Building a seamless UI/UX that works well for both teachers and students
- Managing state and navigation in a complex mobile app architecture

## Accomplishments that we're proud of
- Developed an intuitive interface that simplifies exam creation and management
- Successfully integrated AI grading capabilities that save teachers significant time
- Created a scalable architecture that supports multiple platforms and future features
- Built a comprehensive classroom management system that works alongside Google Classroom
- Designed a system that could potentially save teachers hundreds of hours annually

## What we learned
- The importance of user research in understanding teacher needs
- Complexities of building cross-platform applications
- Best practices for implementing AI in educational technology
- Challenges of creating intuitive UIs for complex functionalities
- Experience with modern mobile development frameworks and tools

## What's next for TeachSuites
- Launch iOS version and web platform
- Expand AI capabilities for more question types
- Add advanced analytics for student performance tracking
- Implement collaborative features for teacher question sharing
- Develop offline mode for areas with limited connectivity
- Add more customization options for different subjects and teaching styles
- Integrate with more educational platforms beyond Google Classroom
