#!/usr/bin/env python3
"""
Simple WAV to MP3 Converter

A simplified version for demonstration and basic usage.
Requires: pydub and ffmpeg

Usage:
    python simple_wav_to_mp3.py input.wav output.mp3
"""

import sys
import os

def check_dependencies():
    """Check if required dependencies are available."""
    try:
        from pydub import AudioSegment
        return True, None
    except ImportError as e:
        return False, str(e)

def convert_wav_to_mp3(input_file, output_file):
    """
    Convert a WAV file to MP3 format.
    
    Args:
        input_file (str): Path to input WAV file
        output_file (str): Path to output MP3 file
    """
    try:
        from pydub import AudioSegment
        
        print(f"Loading WAV file: {input_file}")
        
        # Load the WAV file
        audio = AudioSegment.from_wav(input_file)
        
        print(f"Converting to MP3: {output_file}")
        
        # Export as MP3
        audio.export(output_file, format="mp3")
        
        print(f"✅ Conversion successful!")
        print(f"   Input:  {input_file}")
        print(f"   Output: {output_file}")
        return True
        
    except Exception as e:
        print(f"❌ Error during conversion: {e}")
        return False

def main():
    """Main function."""
    print("Simple WAV to MP3 Converter")
    print("=" * 30)
    
    # Check command line arguments
    if len(sys.argv) != 3:
        print("Usage: python simple_wav_to_mp3.py <input.wav> <output.mp3>")
        print("\nExample:")
        print("  python simple_wav_to_mp3.py song.wav song.mp3")
        sys.exit(1)
    
    input_file = sys.argv[1]
    output_file = sys.argv[2]
    
    # Check if input file exists
    if not os.path.exists(input_file):
        print(f"❌ Error: Input file '{input_file}' does not exist.")
        sys.exit(1)
    
    # Check if input is a WAV file
    if not input_file.lower().endswith('.wav'):
        print(f"❌ Error: Input file '{input_file}' is not a WAV file.")
        sys.exit(1)
    
    # Check dependencies
    deps_ok, error_msg = check_dependencies()
    if not deps_ok:
        print(f"❌ Missing dependencies: {error_msg}")
        print("\nTo install dependencies:")
        print("1. Create virtual environment: python3 -m venv wav_env")
        print("2. Activate it: source wav_env/bin/activate")
        print("3. Install pydub: pip install pydub")
        print("4. Install FFmpeg: sudo apt install ffmpeg (Ubuntu/Debian)")
        print("\nOr run the setup script: ./setup.sh")
        sys.exit(1)
    
    # Create output directory if it doesn't exist
    output_dir = os.path.dirname(output_file)
    if output_dir and not os.path.exists(output_dir):
        os.makedirs(output_dir)
        print(f"Created output directory: {output_dir}")
    
    # Perform conversion
    success = convert_wav_to_mp3(input_file, output_file)
    
    if success:
        # Show file sizes
        input_size = os.path.getsize(input_file)
        output_size = os.path.getsize(output_file)
        compression_ratio = (1 - output_size / input_size) * 100
        
        print(f"\nFile Information:")
        print(f"  Input size:  {input_size:,} bytes")
        print(f"  Output size: {output_size:,} bytes")
        print(f"  Compression: {compression_ratio:.1f}%")
    else:
        sys.exit(1)

if __name__ == "__main__":
    main()