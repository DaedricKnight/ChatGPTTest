#!/bin/bash

echo "WAV to MP3 Converter Setup Script"
echo "================================="

# Check if Python 3 is installed
if ! command -v python3 &> /dev/null; then
    echo "❌ Python 3 is not installed. Please install Python 3 first."
    exit 1
fi

echo "✅ Python 3 found: $(python3 --version)"

# Install FFmpeg
echo ""
echo "Installing FFmpeg..."

if command -v apt &> /dev/null; then
    # Ubuntu/Debian
    echo "Detected apt package manager (Ubuntu/Debian)"
    sudo apt update
    sudo apt install -y ffmpeg python3-venv python3-pip
elif command -v dnf &> /dev/null; then
    # Fedora
    echo "Detected dnf package manager (Fedora)"
    sudo dnf install -y ffmpeg python3-venv python3-pip
elif command -v yum &> /dev/null; then
    # CentOS/RHEL
    echo "Detected yum package manager (CentOS/RHEL)"
    sudo yum install -y epel-release
    sudo yum install -y ffmpeg python3-venv python3-pip
else
    echo "⚠️  Could not detect package manager. Please install FFmpeg manually:"
    echo "   - Ubuntu/Debian: sudo apt install ffmpeg"
    echo "   - Fedora: sudo dnf install ffmpeg"
    echo "   - CentOS/RHEL: sudo yum install epel-release && sudo yum install ffmpeg"
fi

# Check if FFmpeg was installed successfully
if command -v ffmpeg &> /dev/null; then
    echo "✅ FFmpeg installed successfully: $(ffmpeg -version 2>&1 | head -n1)"
else
    echo "❌ FFmpeg installation failed. Please install it manually."
    exit 1
fi

# Create virtual environment
echo ""
echo "Creating Python virtual environment..."
python3 -m venv wav_converter_env

echo "✅ Virtual environment created"

# Activate virtual environment and install Python dependencies
echo ""
echo "Installing Python dependencies..."
source wav_converter_env/bin/activate
pip install -r requirements.txt

if [ $? -eq 0 ]; then
    echo "✅ Python dependencies installed successfully"
else
    echo "❌ Failed to install Python dependencies"
    exit 1
fi

echo ""
echo "🎉 Setup completed successfully!"
echo ""
echo "To use the converter:"
echo "1. Activate the virtual environment: source wav_converter_env/bin/activate"
echo "2. Run the converter: python wav_to_mp3_converter.py input.wav output.mp3"
echo "3. To deactivate when done: deactivate"
echo ""
echo "Example usage:"
echo "  source wav_converter_env/bin/activate"
echo "  python wav_to_mp3_converter.py song.wav song.mp3"
echo "  python wav_to_mp3_converter.py --help"