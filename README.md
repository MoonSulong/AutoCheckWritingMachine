# AutoCheckWritingMachine

## Instruction:
This is a small Java project to covert user input from US dollars into text

- Download or git clone this folder `https://github.com/MoonSulong/AutoCheckWritingMachine.git`
- Open the java project in an IDE, such as `Eclipse`
- Check all the files in the folder, there are an `AutoCheckWritingMachine.java` and an `example.txt`
- Simply run it and output should be displayed in the console
- If run the project in Linux (you may change the path of `example.txt` file)
    ```
    $ javac AutoCheckWritingMachine.java
    $ java AutoCheckWritingMachine
    ```
## Sample result:
```
Illegal Test: 
 -> The formate of input dollar ammount is invalid.
123 -> The formate of input dollar ammount is invalid.
$1.2.3 -> The formate of input dollar ammount is invalid.
$1,&^ -> The formate of input dollar ammount is invalid.
```
```
Input from a file: 
$0.32 -> Zero and 32/100 dollars
$19 -> Nineteen dollars
$42,093.23 -> Forty-two thousand ninety-three and 23/100 dollars
$98,432,905,593.12 -> Ninety-eight billion four hundred thirty-two million nine hundred five thousand five hundred ninety-three and 12/100 dollars
$1,098,432,905,593.12 -> One trillion ninety-eight billion four hundred thirty-two million nine hundred five thousand five hundred ninety-three and 12/100 dollars
```