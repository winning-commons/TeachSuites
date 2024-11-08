#!/bin/bash

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

echo "Build complete!"
