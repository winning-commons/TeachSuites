#!/bin/bash

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Print with color
print_color() {
    printf "${2}%s${NC}\n" "$1"
}

# Print step
print_step() {
    print_color "➡️  $1" "${BLUE}"
}

# Print success
print_success() {
    print_color "✅ $1" "${GREEN}"
}

# Print error
print_error() {
    print_color "❌ $1" "${RED}"
}

# Create directory if it doesn't exist
create_dir() {
    if [ ! -d "$1" ]; then
        mkdir -p "$1"
        print_success "Created directory: $1"
    fi
}

# Create file with content
create_file() {
    if [ ! -f "$1" ]; then
        echo "$2" > "$1"
        print_success "Created file: $1"
    fi
}

print_step "Creating TeachSuites project structure..."

# Create root directories
dirs=(
    ".github/workflows"
    ".vscode"
    "ios/TeachSuites"
    "android/app"
    "web/src"
    "web/public"
    "backend/src"
    "backend/tests"
    "shared/types"
    "shared/constants"
    "shared/utils"
    "docs/api"
    "docs/setup"
    "docs/architecture"
    "scripts"
)

for dir in "${dirs[@]}"; do
    create_dir "$dir"
done

# Create root package.json
ROOT_PACKAGE='{
  "name": "teachsuites",
  "private": true,
  "workspaces": [
    "web",
    "backend",
    "shared"
  ],
  "scripts": {
    "start:web": "yarn workspace web start",
    "start:backend": "yarn workspace backend start",
    "build:all": "./scripts/build-all.sh",
    "test:all": "./scripts/test-all.sh"
  }
}'
create_file "package.json" "$ROOT_PACKAGE"

# Create web package.json
WEB_PACKAGE='{
  "name": "@teachsuites/web",
  "version": "0.1.0",
  "private": true,
  "dependencies": {
    "react": "^18.2.0",
    "react-dom": "^18.2.0"
  }
}'
create_file "web/package.json" "$WEB_PACKAGE"

# Create backend package.json
BACKEND_PACKAGE='{
  "name": "@teachsuites/backend",
  "version": "0.1.0",
  "private": true,
  "dependencies": {
    "express": "^4.18.2",
    "typescript": "^5.0.0"
  }
}'
create_file "backend/package.json" "$BACKEND_PACKAGE"

# Create shared package.json
SHARED_PACKAGE='{
  "name": "@teachsuites/shared",
  "version": "0.1.0",
  "private": true,
  "main": "index.js",
  "types": "index.d.ts"
}'
create_file "shared/package.json" "$SHARED_PACKAGE"

# Create README.md
README='# TeachSuites

A comprehensive teaching and assessment platform.

## Project Structure

```
TeachSuites/
├── ios/              # iOS app
├── android/          # Android app
├── web/              # React web app
├── backend/          # Node.js backend
├── shared/           # Shared code
└── docs/             # Documentation
```

## Getting Started

1. Install dependencies:
   ```bash
   yarn install
   ```

2. Start development servers:
   ```bash
   # Backend
   yarn start:backend

   # Web
   yarn start:web
   ```

## Documentation

See the [docs](./docs) directory for detailed documentation.
'
create_file "README.md" "$README"

# Create .gitignore
GITIGNORE='# Dependencies
node_modules/
.pnp
.pnp.js

# Testing
coverage/

# Production
build/
dist/

# Misc
.DS_Store
.env.local
.env.development.local
.env.test.local
.env.production.local

# Logs
npm-debug.log*
yarn-debug.log*
yarn-error.log*

# IDE
.idea/
*.iml
.vscode/

# iOS
ios/Pods/
*.xcworkspace
*.pbxuser
*.mode1v3
*.mode2v3
*.perspectivev3
*.xcuserstate
project.xcworkspace/
xcuserdata/

# Android
*.iml
.gradle/
local.properties
/.idea
.DS_Store
/build
/captures
.externalNativeBuild
.cxx
'
create_file ".gitignore" "$GITIGNORE"

# Create basic workflow
GITHUB_WORKFLOW='name: CI

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      
      - name: Setup Node.js
        uses: actions/setup-node@v2
        with:
          node-version: "18"
          
      - name: Install Dependencies
        run: yarn install
        
      - name: Run Tests
        run: yarn test:all
'
create_file ".github/workflows/ci.yml" "$GITHUB_WORKFLOW"

# Create VSCode settings
VSCODE_SETTINGS='{
  "editor.formatOnSave": true,
  "editor.defaultFormatter": "esbenp.prettier-vscode",
  "editor.codeActionsOnSave": {
    "source.fixAll.eslint": true
  }
}'
create_file ".vscode/settings.json" "$VSCODE_SETTINGS"

# Create build script
BUILD_SCRIPT='#!/bin/bash

echo "Building all packages..."

# Build shared
echo "Building shared package..."
cd shared && yarn build

# Build backend
echo "Building backend..."
cd ../backend && yarn build

# Build web
echo "Building web..."
cd ../web && yarn build

echo "Build complete!"'
create_file "scripts/build-all.sh" "$BUILD_SCRIPT"

# Make scripts executable
chmod +x scripts/build-all.sh

# Create basic type definition
TYPE_DEF='export interface User {
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
}'
create_file "shared/types/index.ts" "$TYPE_DEF"

print_success "Project structure created successfully!"
print_step "Next steps:"
echo "1. Run 'yarn install' to install dependencies"
echo "2. Set up iOS development environment"
echo "3. Set up Android development environment"
echo "4. Start development with 'yarn start:backend' and 'yarn start:web'"
