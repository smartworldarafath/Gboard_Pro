import os
import re
import xml.etree.ElementTree as ET
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
ERRORS = []


def check_xml(path: Path):
    try:
        ET.parse(path)
    except Exception as exc:
        ERRORS.append(f"Invalid XML/plist in {path}: {exc}")


def check_swift_syntax_heuristics(path: Path):
    text = path.read_text(encoding="utf-8")
    stack = []
    pairs = {"{": "}", "(": ")", "[": "]"}
    closing = {v: k for k, v in pairs.items()}
    in_line_comment = False
    in_block_comment = False
    in_string = False
    escape = False
    i = 0
    n = len(text)
    while i < n:
        ch = text[i]
        nxt = text[i + 1] if i + 1 < n else ""
        if in_line_comment:
            if ch == "\n":
                in_line_comment = False
        elif in_block_comment:
            if ch == "*" and nxt == "/":
                in_block_comment = False
                i += 1
        elif in_string:
            if escape:
                escape = False
            elif ch == "\\":
                escape = True
            elif ch == '"':
                in_string = False
        else:
            if ch == "/" and nxt == "/":
                in_line_comment = True
                i += 1
            elif ch == "/" and nxt == "*":
                in_block_comment = True
                i += 1
            elif ch == '"':
                in_string = True
            elif ch in pairs:
                stack.append(ch)
            elif ch in closing:
                if not stack or stack[-1] != closing[ch]:
