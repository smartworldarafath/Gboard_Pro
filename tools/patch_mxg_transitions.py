path = r'temp_decompiled\smali_classes2\mxg.smali'
with open(path, 'r', encoding='utf-8') as f:
    code = f.read()

target_b = """.method protected final B(Lad;)Lbg;
    .locals 1

    .line 1
    invoke-virtual {p0}, Lag;->fm()Lay;

    .line 2
    .line 3
    .line 4
    move-result-object p0

    .line 5
    new-instance v0, Lc;

    .line 6
    .line 7
    invoke-direct {v0, p0}, Lc;-><init>(Lay;)V

    const p0, 0x7f0b01e0"""

replacement_b = """.method protected final B(Lad;)Lbg;
    .locals 1

    invoke-virtual {p0}, Lag;->fm()Lay;

    move-result-object p0

    new-instance v0, Lc;

    invoke-direct {v0, p0}, Lc;-><init>(Lay;)V

    const p0, 0x1001

    iput p0, v0, Lbg;->i:I

    const p0, 0x010a0000

    iput p0, v0, Lbg;->e:I

    const p0, 0x010a0001

    iput p0, v0, Lbg;->f:I

    const p0, 0x010a0000

    iput p0, v0, Lbg;->g:I

    const p0, 0x010a0001

    iput p0, v0, Lbg;->h:I

    const p0, 0x7f0b01e0"""

target_c = """    new-instance v0, Lc;

    .line 19
    .line 20
    invoke-direct {v0, p0}, Lc;-><init>(Lay;)V

    const p0, 0x7f0b0225"""

replacement_c = """    new-instance v0, Lc;

    invoke-direct {v0, p0}, Lc;-><init>(Lay;)V

    const p0, 0x1001

    iput p0, v0, Lbg;->i:I

    const p0, 0x010a0000

    iput p0, v0, Lbg;->e:I

    const p0, 0x010a0001

    iput p0, v0, Lbg;->f:I

    const p0, 0x010a0000

    iput p0, v0, Lbg;->g:I

    const p0, 0x010a0001

    iput p0, v0, Lbg;->h:I

    const p0, 0x7f0b0225"""

if target_b in code and target_c in code:
    code = code.replace(target_b, replacement_b)
    code = code.replace(target_c, replacement_c)
    with open(path, 'w', encoding='utf-8') as f:
        f.write(code)
    print("Patched mxg.smali animations successfully!")
else:
    print("Error: Targets not found in mxg.smali")
