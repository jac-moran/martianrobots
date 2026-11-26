# Martian Robots

## Building and running
This is a Java Maven project. A single `jar` file, handy for manual runs, can be built with:

`mvn package`

Unit tests are included. The ones in `RunnerTest` are a bit bigger
than your usual unit test, but they are interesting as end-to-end testing.
Tests can be run separately with:

`mvn test`

To run a case, we can invoke the `jar` file created above using any of the test data files included (or create a new one):

`java -classpath ./target/martianrobots-1.0-SNAPSHOT.jar com.jacintomoran.martianrobots.Main ./src/test/resources/sample0.txt`

or use stdin:

`cat src/test/resources/sample0.txt | java -classpath target/martianrobots-1.0-SNAPSHOT.jar com.jacintomoran.martianrobots.Main`

or in a Windows box:

`java -classpath .\target\martianrobots-1.0-SNAPSHOT.jar com.jacintomoran.martianrobots.Main .\src\test\resources\sample0.txt`

This displays the resulting output in the format described in the document.

A logger that writes to stdout can be used by adding the `--stdout-log` switch.

## Structure and decisions
The `Main` convenience class instantiates and runs a `Runner`1` object, which carries out all the validations
and initialisations needed. All input data is validated, and then the objects
involved in the run are created:
* `MarsGrid` - representing the Martian grid with the size as configured. This object includes the scents that have been left by other robots.
* A list of `Robot` objects, as many as have been configured in the input data.
The `Runner` then runs the path defined in every robot.

`Robot` objects are defined by:
* a position in the map, as a `GridPoint` object.
* an orientation, defined by a `GridDirection` object.
`GridPoint` and `GridDirection` contain the logic needed to move a point across the grid, including turns and forward steps.

Specifically, the `GridDirection` object performs any of the movements: a direction knows what happens when turning left or right, and also what it actually means to move forward.
That is why the `getMovedPoint`, `left` and `right` methods, which calculate what happens with each kind of movement, are in `GridDirection`.

But then, even though calculations are performed in these, it's a `Robot` the one that actually moves, so the main movement-related logic (including checking for and setting scents) is there in the `doNextStep` method.
All this logic will normally be a good thing to log, that is why the`Robot` objects also receive the logger objects that have been added to the runner.

## Logging
Loggers are added to a set as the `Logger` interface, thus being possible to add different types of loggers, and more than one at a time.

This structure also allows for adding file-based loggers, or others sending log messages to external services.

## Further options not considered
I would have liked to take another look at tests, to find if they can be made more thorough. A coverage calculation would have been good too, since there are surely uncovered areas in `Runner` and `Main`.

Speaking of loggers, my main reason for coding a `Set` of abstract loggers was the creation of a `GUILogger` (mostly vibe coded), which opens a UI (Swing) window and shows the robots moving across the grid.
Movements are shown, lost robots are marked as such, and scents are also marked so that ignored movements are also displayed.

This `GUILogger` is not included in this release to avoid further complexity. Please let me know if you are interested.
