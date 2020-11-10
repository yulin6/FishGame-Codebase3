## List of Addressed Bugs

The executable we created for integration testing for Milestone 3 failed due to not adding 
"#!/bin/bash" to the top of our executable file, causing it not to be runnable by the test fest
 harness.
- Failing integration test executable prior to fix: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/05e1ba9223fbb1a6eb43ab5cd0aade99dd94a3c8#diff-347934b3c3deec68d1d37e86391e0f3f
- Fix:  https://github.ccs.neu.edu/CS4500-F20/fritch/commit/b165f46fade8b2c91b83c45ffe6f86fb3eeaed8f#diff-347934b3c3deec68d1d37e86391e0f3fL1-R2

The code we wrote for the integration testing for Milestone 4 failed due to our misunderstanding
 of the JSON specifications. In particular, we thought that the JSON array of players should be
  maintained in the order it was given, when in actuality the first player in the array is the
   current player, and the second is the next, etc. We corrected this by adding ordering in our
    set data structure holding players, 
- Commit containing integration testing code with bug: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/3c1e628103d45cbb110e3a9932c92a42771fd10e 
- Fix:  https://github.ccs.neu.edu/CS4500-F20/fritch/commit/f28ba6eb1ccca5db44ce520253d450933b3f4121
(Specifically, the changes made were to State.java and Xstate.java. The JSON tests we wrote were
 also changed to reflect the correct understanding of the requirements.)


