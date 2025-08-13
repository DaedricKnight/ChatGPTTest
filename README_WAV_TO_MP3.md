# WAV to MP3 Converter

A Python script that converts WAV audio files to MP3 format using the pydub library.

## Features

- Convert single WAV files to MP3
- Batch convert all WAV files in a directory
- Customizable bitrate and quality settings
- Cross-platform compatibility (Windows, macOS, Linux)
- Detailed progress output and error handling

## Installation

### 1. Install Python Dependencies

```bash
pip install -r requirements.txt
```

### 2. Install FFmpeg (Required)

The script requires FFmpeg for MP3 encoding. Install it based on your operating system:

#### Ubuntu/Debian:
```bash
sudo apt update
sudo apt install ffmpeg
```

#### CentOS/RHEL/Fedora:
```bash
sudo dnf install ffmpeg
# or for older versions:
sudo yum install ffmpeg
```

#### macOS (with Homebrew):
```bash
brew install ffmpeg
```

#### Windows:
1. Download FFmpeg from [https://ffmpeg.org/download.html](https://ffmpeg.org/download.html)
2. Extract and add the `bin` folder to your system PATH
3. Or use chocolatey: `choco install ffmpeg`

## Usage

### Convert a Single File

```bash
python wav_to_mp3_converter.py input.wav output.mp3
```

### Convert All WAV Files in a Directory

```bash
python wav_to_mp3_converter.py ./wav_files/ ./mp3_files/
```

### Advanced Options

```bash
# High quality conversion (320k bitrate, quality 0)
python wav_to_mp3_converter.py input.wav output.mp3 --bitrate 320k --quality 0

# Lower quality conversion (128k bitrate, quality 5)
python wav_to_mp3_converter.py input.wav output.mp3 --bitrate 128k --quality 5
```

### Command Line Options

- `--bitrate, -b`: MP3 bitrate (default: 192k)
  - Common values: 128k, 192k, 256k, 320k
- `--quality, -q`: MP3 quality (0-9, lower is better, default: 2)
  - 0 = highest quality, largest file
  - 9 = lowest quality, smallest file

### Help

```bash
python wav_to_mp3_converter.py --help
```

## Examples

### Single File Conversion
```bash
# Basic conversion
python wav_to_mp3_converter.py song.wav song.mp3

# High quality conversion
python wav_to_mp3_converter.py song.wav song.mp3 -b 320k -q 0
```

### Batch Conversion
```bash
# Convert all WAV files in music folder to mp3 folder
python wav_to_mp3_converter.py ./music/ ./mp3/

# Convert with custom settings
python wav_to_mp3_converter.py ./music/ ./mp3/ --bitrate 256k --quality 1
```

## File Structure

After running the converter, your files will be organized as follows:

```
your_project/
├── wav_to_mp3_converter.py
├── requirements.txt
├── input_files/
│   ├── song1.wav
│   ├── song2.wav
│   └── song3.wav
└── output_files/
    ├── song1.mp3
    ├── song2.mp3
    └── song3.mp3
```

## Troubleshooting

### "Could not decode" Error
- Ensure the input file is a valid WAV file
- Check if the file is corrupted or has an unsupported format

### "FFmpeg not found" Error
- Make sure FFmpeg is installed and accessible from command line
- Try running `ffmpeg -version` to verify installation

### Permission Errors
- Ensure you have read permissions for input files
- Ensure you have write permissions for the output directory

### Memory Issues with Large Files
- The script loads entire audio files into memory
- For very large files, consider using a streaming approach or splitting the file

## Technical Details

- **Library**: pydub (Python audio manipulation)
- **Encoding**: FFmpeg (for MP3 encoding)
- **Supported Input**: WAV files (both .wav and .WAV extensions)
- **Output Format**: MP3 with customizable bitrate and quality
- **Python Version**: 3.6+