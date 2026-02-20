# Report for assignment 3

## Project

Name: Elinor Selinder, Hannes Westerberg, Helin Saeid, Liza Aziz & Omar Almassri

URL (forked repo): https://github.com/Ramso127/commons-imaging-group13-DD2480 

One or two sentences describing it. **TODO**

## Onboarding experience

We had some small issues, especially for Window users. We noticed that the cloned repository could not be inside a folder with any spacing in its name. Other than that, onboarding went smoothly, with only a few terminal commands (mentioned in README.md) to compile the test and build. At first we had initially chosen to work with _apache common text_. However, we quickly noticed that all of the functions had almost 100% branch coverage, which made it harder for us to make any improvements. Therefore, we chose to work with _apache common imaging_ instead, since it had more room for coverage improvement.

Did it build and run as documented? See the assignment for details; if everything works out of the box, there is no need to write much here. If the first project(s) you picked
ended up being unsuitable, you can describe the "onboarding experience"
for each project, along with reason(s) why you changed to a different one. **TODO**

## Complexity
* **nextToken** (`/BasicCParser.java`): matched our manual count with the Jacoco report and lizard terminal log. The results were at first not clear, but we learned that CNN represented the  size of complexity. This function is a high complex function, but with an average amount of code. The overall code was not too complicated to understand as well. The **purpose** of this method is to read each letter and symbol in a stream of characters and group them into meaningful units. It reads from a XPM image file (C code) and creates these tokens so the image parser can process the file rather than reading it character by character. Lizard, metric tool, did not take exceptions into account. If it had done so, the CC would have increased to **32**. The documentation for **nextToken** is not clear. It fails to explain the specific conditions that trigger each branch. It only mentions when the exceptions will be thrown, but nothing more than that.

- **decompress** (`AbstractImageDataReader.java`): the **purpose** of this method is to take compressed bytes from a TIFF image and route them to the correct decompression algorithm. Lizard reported a CC of **21**, without taking exceptions into account (would be 25 in that case). The function is about 100 lines long and the high CC comes from the many compression types in the switch statement rather than deeply nested logic. The documentation only describes the parameters and return value but does not explain the different outcomes or error cases.

* Did all methods (tools vs. manual count) get the same result? **TODO**
* Are the results clear? **TODO**
1. What are your results for five complex functions? **TODO**
2. Are the functions just complex, or also long? **TODO**
3. What is the purpose of the functions? **TODO**
4. Are exceptions taken into account in the given measurements? **TODO**
5. Is the documentation clear w.r.t. all the possible outcomes? **TODO**

## Refactoring

Plan for refactoring complex code:
* nextToken (`/BasicCParser.java`): its high complexity is not necessary, since it handles a lot of if-conditions for different states of the quote. This can be easily be divided in to one "main" function _nextToken_ which calls on other helper methods. These methods will handle the specific logic for strings, identifiers and standard characters respectively. To allow these methods (approx 3) to share the data, the local variables (inString, inIdentifier and token) will be promoted to private class fields. This would definetly lower the CC, to perhaps lower than 10, since it will only have a few if-blocks to call each helper method. Since if the plan is to transfer local variables outside of the main method, it is important to ensure that they are reset everytime nextToken runs, to avoid any effects on the tokens.

- decompress (`AbstractImageDataReader.java`): the method can be simplified by splitting it up. The switch statement has 8 cases for different compression types, and some of them contain additional if-checks. Each case could be extracted into its own private helper method. The main decompress() would then only handle the fill order check and call the right helper via the switch. This would reduce the CC from 21 to about 12, since the nested if-checks inside the cases move into the helpers. A drawback is more methods in the class, but each method would only do one thing making them simpler to understand and test.

Estimated impact of refactoring (lower CC, but other drawbacks?). **TODO**

Carried out refactoring (optional, P+): **TODO**

git diff ...

## Coverage

### Tools
We felt that using both our own DIY coverage tool and Jacoco  to be quite easy. The only "difficult" part with Jacoco is to know where to find the index.html file and also how to navigate through it. But once we understood how to get to each class and the methods, it was quite easy to see how the code behaves before we covered any of the missing branches.

We used both lizard and Jacoco to see the amount of CC in each method as well as to see the amount of missing branches. For lizard and Jacoco report, it was quite easy for us to both download and use it in our terminal: 
```
pip install lizard 
lizard src/main/java -C 15
```
```
mvn clean test jacoco:report -Pjacoco
```
However, none of this was documented in the original repo (inside `README.md`), however these were not very difficult to understand and find the correct terminal output.

* Document your experience in using a "new"/different coverage tool.
How well was the tool documented? Was it possible/easy/difficult to
integrate it with your build environment? **TODO**

### Your own coverage tool
* Following is a link to a branch which presents how DIY coverage tool was utilized in nextToken (`/BasicCParser.java`):
https://github.com/Ramso127/commons-imaging-group13-DD2480/tree/3-feature/diy-coverage-liza 

* Show a patch (or link to a branch) that shows the instrumented code to
gather coverage measurements. **TODO**

* The patch is probably too long to be copied here, so please add
the git command that is used to obtain the patch instead: **TODO**

git diff ...

* What kinds of constructs does your tool support, and how accurate is
its output? **TODO**

* Our DIY coverage tool records every **hit counts** and not just a binary "hit or miiss" report. This allowed us to see not only if the code was executed, but how often
Our tool is limited to the specific branches that we manually instrumented. It cannot measure any e.g. &&- or ||-conditions individually. It also cannot capture verify if an exception occurred halfway through the code, only if it ran or not.

### Evaluation
1. How detailed is your coverage measurement? **TODO**
2. What are the limitations of your own tool? **TODO**
3. Are the results of your tool consistent with existing coverage tools? **TODO**
* nextToken (`/BasicCParser.java`): it was consistent for this method, however it had some limitations, e.g. not being able to handle ||-operations and specifically hitting each complexity. To solve that, it would only show the combined for the if-block was hit or not. It is also not very detailed, for the same reasons mentioned.
- decompress (`AbstractImageDataReader.java`): consistent with Jacoco. Our DIY tool reported 0 hits for branches 4, 5, 6, 12, 17, 19, and 24. Jacoco confirmed the same uncovered branches. After adding two new tests, both tools agree that branches 6 and 24 are now covered.

## Coverage improvement

Show the comments that describe the requirements for the coverage. **TODO**

Report of old coverage: [link] **TODO**

Report of new coverage: [link] **TODO**

[Old coverage for nextToken](docs/images/nextToken/Before)

[New coverage for nextToken](docs/images/nextToken/After)

decompress (`AbstractImageDataReader.java`):

- [Old coverage for decompress](docs/images/decompress/Before)

- [New coverage for decompress](docs/images/decompress/After)

Test cases added:
* nextToken (`/BasicCParser.java`) with test files and commenting:
https://github.com/Ramso127/commons-imaging-group13-DD2480/tree/3-feature/refactor-liza

- decompress (`AbstractImageDataReader.java`) with test file and commenting:
https://github.com/Ramso127/commons-imaging-group13-DD2480/tree/8-feature/diy-coverage-omar

git diff ...

Number of test cases added: two per team member (P) or at least four (P+). **TODO**
* Liza Aziz: 2 tests (P)
* Omar Almassri: 2 tests (P)

## Self-assessment: Way of working

Current state according to the Essence standard: p. 58 in the Essence standard v1.2

Was the self-assessment unanimous? Any doubts about certain items?

How have you improved so far?

Where is potential for improvement?

## Overall experience

We learned how to fork and clone and make contributions to an open source repository. We also learned how to jump in to an already established project and research any potential parts that could be improvements - all from updating README, any dependencies or terminal commands to know, and adding new tests that hits any missing branches. It was fun for us to learn a new part of working with coding and contributions to external projects.

What are your main take-aways from this project? What did you learn? **TODO**

Is there something special you want to mention here? **TODO**
