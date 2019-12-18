# Paramorphism 2.0 Deobfuscated Source Leak

i was very disappointed to see the new para 2.0 release. i had predicted it would be full of quality strategies, things that would be demotivating to a cracker.

instead i saw an invokedynamic decryptor weaker than zelix, flow obfuscation.. if you can even call it that.
what is the point of disabling the verifier if there is only a handful of instructions that break decompilers and 5 minutes of asm customization?

## You are not safe using Paramorphism 2.0

i dont see an issue with someone trying to make an obfuscator, but behind the flashy analysis crashers, your code is almost lossless.

- Patched ASM Exploits
- Removed Invalid Bytecode
- Removed _protected_by_paramorphism methods
- Removed String Concealing (Invoke Dynamic)
- Removed Method Indirection (Invoke Dynamic)
- Replaced Conditional Jumps with Goto
- Removed Synthetic
- Removed Kotlin Nonnull checks
- Recovered ASM Opcode field calls
- Manually remapped classes, fields, and methods

## More thorough source leak later
