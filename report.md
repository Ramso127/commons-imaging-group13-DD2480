# Report for assignment 3

## Project

Name: Elinor Selinder, Hannes Westerberg, Helin Saeid, Liza Aziz & Omar Almassri

URL (forked repo): https://github.com/Ramso127/commons-imaging-group13-DD2480

Apache Commons Imaging is a pure-Java image library for reading and writing a variety of image formats. This fork is used by Group 13 in the DD2480 course to analyze and improve code complexity, test coverage, and refactoring practices.

## Onboarding experience

We had some small issues, especially for Window users. We noticed that the cloned repository could not be inside a folder with any spacing in its name. Other than that, onboarding went smoothly, with only a few terminal commands (mentioned in README.md) to compile the test and build. At first we had initially chosen to work with _apache common text_. However, we quickly noticed that all of the functions had almost 100% branch coverage, which made it harder for us to make any improvements. Therefore, we chose to work with _apache common imaging_ instead, since it had more room for coverage improvement.

## Complexity

- **nextToken** (`/BasicCParser.java`): matched our manual count with the Jacoco report and lizard terminal log. The results were at first not clear, but we learned that CNN represented the size of complexity. This function is a high complex function, but with an average amount of code. The overall code was not too complicated to understand as well. The **purpose** of this method is to read each letter and symbol in a stream of characters and group them into meaningful units. It reads from a XPM image file (C code) and creates these tokens so the image parser can process the file rather than reading it character by character. Lizard, metric tool, did not take exceptions into account. If it had done so, the CC would have increased to **32**. The documentation for **nextToken** is not clear. It fails to explain the specific conditions that trigger each branch. It only mentions when the exceptions will be thrown, but nothing more than that.

- getImageInfo (`\PngImageParser.java`): The manual count mathed with the Jacoco report (17). However, there was a mismatch between the second person counting (21). However, when acounting for a switch case which could be counted depending on if each case, even if they will fall through to next case. So this is reasonalble, and still correct. The exceptions are not taken into account in the calculation and the documentation of the function and tests were lacking, and all possible coutcomes were not documented. The **purpose** of getImageInfo is that it reads a PNG file's chunks and extracts all metadata eg. dimensions, transparency, DPI, color type, text comments, palette usage, physical scale) into a single PngImageInfo object. It validates that required PNG chunks exist and aren't duplicated, throwing ImagingException for invalid files.

- \*Did all methods (tools vs. manual count) get the same result? **TODO\***

- \*Are the results clear? **TODO\***

1. \*What are your results for five complex functions? **TODO\***

2. \*Are the functions just complex, or also long? **TODO\***

3. \*What is the purpose of the functions? **TODO\***

4. \*Are exceptions taken into account in the given measurements? **TODO\***

5. \*Is the documentation clear w.r.t. all the possible outcomes? **TODO\***

## Refactoring

_Plan for refactoring complex code_:

- nextToken (`/BasicCParser.java`): its high complexity is not necessary, since it handles a lot of if-conditions for different states of the quote. This can be easily be divided in to one "main" function _nextToken_ which calls on other helper methods. These methods will handle the specific logic for strings, identifiers and standard characters respectively. To allow these methods (approx 3) to share the data, the local variables (inString, inIdentifier and token) will be promoted to private class fields. This would definetly lower the CC, to perhaps lower than 10, since it will only have a few if-blocks to call each helper method. Since if the plan is to transfer local variables outside of the main method, it is important to ensure that they are reset everytime nextToken runs, to avoid any effects on the tokens.
  - getImageInfo (`\PngImageParser.java`): There are cases of very identical for loops. Lines 489-506 have 3 for loops which are nearly identical execpt for one variable so this could be refactored into one help of one helper fucntion which could reduce complexity. Furthermore there are many if statements that check the size of a chunk is bigger than 1. These could also be moved to a helper function, with the parameter being the chucnk which size should be checked.

\*Estimated impact of refactoring (lower CC, but other drawbacks?). **TODO\***

- getImageInfo (`\PngImageParser.java`): The estimated complexity should reduce by around 4. While less complexity leads to more readable and less error prone code, this code is not heavily nested, which means it may not be a high priority as compared to functions with very high complexity. There are many functions 30> complexity which should be concidered first, refactoring is labour intensive. The value gained may not be worth the time and effort which could be allocated in more critical areas.

## Coverage

### Tools

We felt that using both our own DIY coverage tool and Jacoco  to be quite easy. The only "difficult" part with Jacoco is to know where to find the index.html file and also how to navigate through it. But once we understood how to get to each class and the methods, it was quite easy to see how the code behaves before we covered any of the missing branches.

We used both lizard and Jacoco to see the amount of CC in each method as well as to see the amount of missing branches. For lizard and Jacoco report, it was quite easy for us to both download and use it in our terminal:

```

pip install lizard

lizard src/main/java -C 15

```

```

mvn clean test jacoco:report -Pjacoco

```

However, none of this was documented in the original repo (inside `README.md`), however these were not very difficult to understand and find the correct terminal output.

- Document your experience in using a "new"/different coverage tool.

How well was the tool documented? Was it possible/easy/difficult to

integrate it with your build environment? **TODO**

### Your own coverage tool

- Following is a link to a branch which presents how DIY coverage tool was utilized in nextToken (`/BasicCParser.java`):

https://github.com/Ramso127/commons-imaging-group13-DD2480/tree/3-feature/diy-coverage-liza

- Show a patch (or link to a branch) that shows the instrumented code to

gather coverage measurements. **TODO**

- The patch is probably too long to be copied here, so please add

the git command that is used to obtain the patch instead: **TODO**

- What kinds of constructs does your tool support, and how accurate is

its output? **TODO**

- Our DIY coverage tool records every **hit counts** and not just a binary "hit or miiss" report. This allowed us to see not only if the code was executed, but how often

Our tool is limited to the specific branches that we manually instrumented. It cannot measure any e.g. &&- or ||-conditions individually. It also cannot capture verify if an exception occurred halfway through the code, only if it ran or not.

### Evaluation

1. \*How detailed is your coverage measurement? **TODO\***

2. \*What are the limitations of your own tool? **TODO\***

3. \*Are the results of your tool consistent with existing coverage tools? **TODO\***

- nextToken (`/BasicCParser.java`): it was consistent for this method, however it had some limitations, e.g. not being able to handle ||-operations and specifically hitting each complexity. To solve that, it would only show the combined for the if-block was hit or not. It is also not very detailed, for the same reasons mentioned.

* getImageInfo (`\PngImageParser.java`): it was consistant for this method. The method had basic decision points such as if and for loops, so this was not unexpected, and therefore all the tools were consistant with eachother.

## Coverage improvement

\*Show the comments that describe the requirements for the coverage. **TODO\***

- Report of old coverage: [link] **TODO**

- Report of new coverage: [link] **TODO**

nextToken (`/BasicCParser.java`

- [Old coverage for nextToken](docs/images/nextToken/Before)

- [New coverage for nextToken](docs/images/nextToken/After)

getImageInfo (`\PngImageParser.java`):

- [Old coverage for getImageInfo](docs\images\nextToken\Before)

- [New coverage for getImageInfo](docs\images\getImageInfo\After)

_Test cases added:_

- nextToken (`/BasicCParser.java`) with test files and commenting:

https://github.com/Ramso127/commons-imaging-group13-DD2480/tree/3-feature/refactor-liza

- getImageInfo (`\PngImageParser.java`) with test files and commenting::

https://github.com/Ramso127/commons-imaging-group13-DD2480/blob/12/feature/diy-coverage-elinor/src/test/java/org/apache/commons/imaging/formats/png/ZZZPngImageParserTest.java

Number of test cases added: two per team member (P) or at least four (P+). **TODO**

- Liza Aziz: 2 tests (P)

- Elinor Selinder: 2 tests (P)

## Self-assessment: Way of working

_Current state according to the Essence standard: p. 58 in the Essence standard v1.2_
We would place ourselves in the **In Use state** of the **Way of Working checklist**. We have established rules and tools for the project and are actively using them. However, we haven't reached the point where they feel natural. We still regularly inspect our ways of working, for example by verifying that issues are linked in GitHub, letting eachother know if the way of working is not as expected, and for example discussing how we should name certain things like branches.

_Was the self-assessment unanimous? Any doubts about certain items?_
We had doubts about different aspects, and we helped eachother understand them. For example, with onboarding and how to manage branches. Therefore we unanimously placed ourselved in the "In Use State".

_How have you improved so far?_

We have improved by unifying our way of working - specifically when it comes to linking issues in every commit title, as this was a weakness pointed out in our previous assignment where the issues were linked rather in the description. We are becoming more confident in the git workflow, committing, creating branches, PR and code reviewing eachother.

_Where is potential for improvement?_

There is further improvement to be gained by solidifying these rules and practicing them until they become second nature and fewer questions arise, while continuing to refine our way of working through ongoing communication with the team.

## Overall experience

We learned how to fork and clone and make contributions to an open source repository. We also learned how to jump in to an already established project and research any potential parts that could be improvements - all from updating README, any dependencies or terminal commands to know, and adding new tests that hits any missing branches. It was fun for us to learn a new part of working with coding and contributions to external projects.

_What are your main take-aways from this project? What did you learn?_
We learned how to navigate large open source projects, and to measure Complexity and Coverage. We learned to locate, and improve branch coverage with tests in this enviornment, specifically with code which we are not the authors of and therefore unfamiliar with. Our main takeaways are the experience of navigating large codebases, and code with high structural complexity - and also to concider how this complexity can be reduce.
