# Report for assignment 3

## Project

Name: Elinor Selinder, Hannes Westerberg, Helin Saeid, Liza Aziz & Omar Almassri

URL (forked repo): https://github.com/Ramso127/commons-imaging-group13-DD2480 

One or two sentences describing it. TODO

## Onboarding experience

We had some small issues, especially for Window users. We noticed that the cloned repository could not be inside a folder with spacing. Other than that, onboarding went smoothly, with only a few terminal commands (mentioned in README.md) to compile the test and build (very quick as well). 

Did it build and run as documented? See the assignment for details; if everything works out of the box, there is no need to write much here. If the first project(s) you picked
ended up being unsuitable, you can describe the "onboarding experience"
for each project, along with reason(s) why you changed to a different one.

## Complexity

1. What are your results for five complex functions?
   * nextToken (`/BasicCParser.java`): matched our manual count with the Jacoco report and lizard terminal log. The results were at first not clear, but we learned that CNN represented the  size of complexity.
   * Did all methods (tools vs. manual count) get the same result?
   * Are the results clear?
2. Are the functions just complex, or also long?
   * nextToken (`/BasicCParser.java`): is a high complex function, but with an okay amount of code, especially since the overall code was not too complicated to understand.
3. What is the purpose of the functions?
   * nextToken (`/BasicCParser.java`): TODO
4. Are exceptions taken into account in the given measurements?
   * nextToken (`/BasicCParser.java`):
5. Is the documentation clear w.r.t. all the possible outcomes?
   * TODO

## Refactoring

Plan for refactoring complex code:
* nextToken (`/BasicCParser.java`): the plan is to...TODO - will not carry out P+

Estimated impact of refactoring (lower CC, but other drawbacks?).

Carried out refactoring (optional, P+):

git diff ...

## Coverage

### Tools
We felt that using both our own DIY coverage tool and the Jacoco report to be quite easy. The only "difficult" part with Jacoco is to know where to find the index.html file and also how to navigate through it. But once we understood how to get to each class and the methods, it was quite easy to see how the code behaves before we covered any of the missing branches.

We used both lizard and Jacoco to see the amount of CC in each method as well as to see the amount of missing branches. For lizard and Jacoco report, it was quite easy for us to both download and use it in our terminal: 
```
pip install lizard 
lizard src/main/java -C 15
```
```
mvn clean test jacoco:report -Pjacoco
```
However, none of this was documented in the original repo (inside `README.md`), however these were not very difficult to understand and find the correct terminal output.

Document your experience in using a "new"/different coverage tool.
How well was the tool documented? Was it possible/easy/difficult to
integrate it with your build environment?

### Your own coverage tool
* Following is a link to a branch which presents how DIY coverage tool was utilized in nextToken (`/BasicCParser.java`):
https://github.com/Ramso127/commons-imaging-group13-DD2480/tree/3-feature/diy-coverage-liza 

Show a patch (or link to a branch) that shows the instrumented code to
gather coverage measurements.

The patch is probably too long to be copied here, so please add
the git command that is used to obtain the patch instead:

git diff ...

What kinds of constructs does your tool support, and how accurate is
its output?
* TODO

### Evaluation

1. How detailed is your coverage measurement?
* TODO

2. What are the limitations of your own tool?
* TODO

3. Are the results of your tool consistent with existing coverage tools?
* nextToken (`/BasicCParser.java`): it was consistent for this method, however it had some limitations, e.g. not being able to handle ||-operations and specifically hitting each complexity. To solve that, it would only show the combined for the if-block was hit or not. It is also not very detailed, for the same reasons mentioned.

## Coverage improvement

Show the comments that describe the requirements for the coverage. TODO

Report of old coverage: [link] TODO

Report of new coverage: [link] TODO

Test cases added:
* nextToken (`/BasicCParser.java`):
https://github.com/Ramso127/commons-imaging-group13-DD2480/tree/3-feature/refactor-liza

git diff ...

Number of test cases added: two per team member (P) or at least four (P+).
* Liza Aziz: 2 tests (P)

## Self-assessment: Way of working

Current state according to the Essence standard: p. 58 in the Essence standard v1.2

Was the self-assessment unanimous? Any doubts about certain items?

How have you improved so far?

Where is potential for improvement?

## Overall experience

We learned how to fork and clone and make contributions to an open source repository. We also learned how to jump in to an already established project and research any potential parts that could be improvements - all from updating README, any dependencies or terminal commands to know, and adding new tests that hits any missing branches. It was fun for us to learn a new part of working with coding and contributions to external projects.

What are your main take-aways from this project? What did you learn?

Is there something special you want to mention here?
