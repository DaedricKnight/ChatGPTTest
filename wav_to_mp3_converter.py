#!/usr/bin/env python3
"""
WAV to MP3 Converter

This script converts WAV audio files to MP3 format using the pydub library.
It supports both single file conversion and batch conversion of multiple files.

Dependencies:
- pydub: For audio file manipulation
- ffmpeg: Required by pydub for MP3 encoding (installed separately)

Usage:
    python wav_to_mp3_converter.py input.wav output.mp3
    python wav_to_mp3_converter.py input_folder/ output_folder/
"""

import os
import sys
import argparse
from pathlib import Path
from pydub import AudioSegment
from pydub.exceptions import CouldntDecodeError


def convert_wav_to_mp3(input_path, output_path, bitrate="192k", quality=2):
    """
    Convert a single WAV file to MP3.
    
    Args:
        input_path (str): Path to the input WAV file
        output_path (str): Path to the output MP3 file
        bitrate (str): MP3 bitrate (default: "192k")
        quality (int): MP3 quality (0-9, lower is better, default: 2)
    
    Returns:
        bool: True if conversion successful, False otherwise
    """
    try:
        # Load WAV file
        print(f"Loading WAV file: {input_path}")
        audio = AudioSegment.from_wav(input_path)
        
        # Convert to MP3
        print(f"Converting to MP3: {output_path}")
        audio.export(
            output_path,
            format="mp3",
            bitrate=bitrate,
            parameters=["-q:a", str(quality)]
        )
        
        print(f"✓ Successfully converted: {input_path} -> {output_path}")
        return True
        
    except CouldntDecodeError:
        print(f"✗ Error: Could not decode {input_path}. Make sure it's a valid WAV file.")
        return False
    except Exception as e:
        print(f"✗ Error converting {input_path}: {str(e)}")
        return False


def batch_convert_directory(input_dir, output_dir, bitrate="192k", quality=2):
    """
    Convert all WAV files in a directory to MP3.
    
    Args:
        input_dir (str): Path to directory containing WAV files
        output_dir (str): Path to output directory for MP3 files
        bitrate (str): MP3 bitrate (default: "192k")
        quality (int): MP3 quality (0-9, lower is better, default: 2)
    
    Returns:
        tuple: (successful_conversions, total_files)
    """
    input_path = Path(input_dir)
    output_path = Path(output_dir)
    
    # Create output directory if it doesn't exist
    output_path.mkdir(parents=True, exist_ok=True)
    
    # Find all WAV files
    wav_files = list(input_path.glob("*.wav")) + list(input_path.glob("*.WAV"))
    
    if not wav_files:
        print(f"No WAV files found in {input_dir}")
        return 0, 0
    
    print(f"Found {len(wav_files)} WAV files to convert")
    
    successful = 0
    for wav_file in wav_files:
        # Create output filename
        mp3_file = output_path / f"{wav_file.stem}.mp3"
        
        if convert_wav_to_mp3(str(wav_file), str(mp3_file), bitrate, quality):
            successful += 1
    
    return successful, len(wav_files)


def validate_paths(input_path, output_path):
    """
    Validate input and output paths.
    
    Args:
        input_path (str): Input file or directory path
        output_path (str): Output file or directory path
    
    Returns:
        tuple: (is_valid, is_batch_mode)
    """
    input_p = Path(input_path)
    
    # Check if input exists
    if not input_p.exists():
        print(f"✗ Error: Input path '{input_path}' does not exist.")
        return False, False
    
    # Determine if this is batch mode (directory) or single file mode
    if input_p.is_dir():
        # Batch mode - converting directory
        return True, True
    elif input_p.is_file():
        # Single file mode
        if not input_path.lower().endswith(('.wav', '.WAV')):
            print(f"✗ Error: Input file '{input_path}' is not a WAV file.")
            return False, False
        
        # Check if output directory exists (create if needed)
        output_p = Path(output_path)
        if output_p.parent != Path('.'):
            output_p.parent.mkdir(parents=True, exist_ok=True)
        
        return True, False
    else:
        print(f"✗ Error: Input path '{input_path}' is neither a file nor a directory.")
        return False, False


def main():
    """Main function to handle command line arguments and conversion."""
    parser = argparse.ArgumentParser(
        description="Convert WAV files to MP3 format",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  # Convert single file
  python wav_to_mp3_converter.py audio.wav audio.mp3
  
  # Convert all WAV files in a directory
  python wav_to_mp3_converter.py ./wav_files/ ./mp3_files/
  
  # Convert with custom bitrate and quality
  python wav_to_mp3_converter.py input.wav output.mp3 --bitrate 320k --quality 0
        """
    )
    
    parser.add_argument(
        "input",
        help="Input WAV file or directory containing WAV files"
    )
    
    parser.add_argument(
        "output",
        help="Output MP3 file or directory for converted files"
    )
    
    parser.add_argument(
        "--bitrate", "-b",
        default="192k",
        help="MP3 bitrate (default: 192k). Examples: 128k, 192k, 256k, 320k"
    )
    
    parser.add_argument(
        "--quality", "-q",
        type=int,
        default=2,
        choices=range(0, 10),
        help="MP3 quality (0-9, lower is better, default: 2)"
    )
    
    # Parse arguments
    args = parser.parse_args()
    
    # Validate paths
    is_valid, is_batch = validate_paths(args.input, args.output)
    if not is_valid:
        sys.exit(1)
    
    print("WAV to MP3 Converter")
    print("=" * 20)
    print(f"Input: {args.input}")
    print(f"Output: {args.output}")
    print(f"Bitrate: {args.bitrate}")
    print(f"Quality: {args.quality}")
    print()
    
    try:
        if is_batch:
            # Batch conversion
            successful, total = batch_convert_directory(
                args.input, args.output, args.bitrate, args.quality
            )
            
            print()
            print(f"Conversion completed: {successful}/{total} files converted successfully")
            
            if successful < total:
                sys.exit(1)
        else:
            # Single file conversion
            success = convert_wav_to_mp3(
                args.input, args.output, args.bitrate, args.quality
            )
            
            if not success:
                sys.exit(1)
        
        print("\n✓ All conversions completed successfully!")
        
    except KeyboardInterrupt:
        print("\n\n✗ Conversion interrupted by user")
        sys.exit(1)
    except Exception as e:
        print(f"\n✗ Unexpected error: {str(e)}")
        sys.exit(1)


if __name__ == "__main__":
    main()