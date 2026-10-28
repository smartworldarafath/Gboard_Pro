with open('temp_decompiled/smali_classes2/pny.smali', 'r', encoding='utf-8') as f:
    content = f.read()

import re

# Update f(I)V to always use the dynamic amplitude & duration scaled one-shot vibration
f_pattern = r'\.method public final f\(I\)V.*?\.end method'
clean_f = '''.method public final f(I)V
    .locals 4

    .line 1
    iget-object p0, p0, Lpny;->j:Lvzq;

    .line 2
    .line 3
    invoke-interface {p0}, Lvzq;->get()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Landroid/os/Vibrator;

    .line 8
    .line 9
    if-nez p0, :cond_0

    .line 10
    .line 11
    return-void

    .line 12
    :cond_0
    # Dynamic amplitude: 1 to 255 mapped linearly from p1 (0 to 100)
    mul-int/lit16 v0, p1, 0xff

    div-int/lit8 v0, v0, 0x64

    const/4 v1, 0x1

    invoke-static {v1, v0}, Ljava/lang/Math;->max(II)I

    move-result v0

    const/16 v1, 0xff

    invoke-static {v1, v0}, Ljava/lang/Math;->min(II)I

    move-result v0

    # Dynamic duration: 3ms to 33ms based on p1
    mul-int/lit8 v1, p1, 0x1e

    div-int/lit8 v1, v1, 0x64

    add-int/lit8 v1, v1, 0x3

    int-to-long v1, v1

    invoke-static {v1, v2, v0}, Lk$$ExternalSyntheticApiModelOutline1;->m(JI)Landroid/os/VibrationEffect;

    move-result-object p1

    :try_start_0
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x21

    if-lt v0, v1, :cond_2

    const/16 v0, 0x12

    invoke-static {v0}, Lt$$ExternalSyntheticApiModelOutline0;->m(I)Landroid/os/VibrationAttributes;

    move-result-object v0

    invoke-static {p0, p1, v0}, Lt$$ExternalSyntheticApiModelOutline0;->m(Landroid/os/Vibrator;Landroid/os/VibrationEffect;Landroid/os/VibrationAttributes;)V

    return-void

    :cond_2
    invoke-static {p0, p1}, Lk$$ExternalSyntheticApiModelOutline1;->m(Landroid/os/Vibrator;Landroid/os/VibrationEffect;)V
    :try_end_0
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_0} :catch_0

    :catch_0
    return-void
.end method'''

content = re.sub(f_pattern, clean_f, content, flags=re.DOTALL)

with open('temp_decompiled/smali_classes2/pny.smali', 'w', encoding='utf-8') as f:
    f.write(content)

print("pny.smali vibration strength fix applied successfully!")
