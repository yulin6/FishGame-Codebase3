# List of Addressed Bugs

## GameState
The executable we created for integration testing for Milestone 3 failed due to not adding 
"#!/bin/bash" to the top of our executable file.
- Failing integration test executable prior to fix: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/05e1ba9223fbb1a6eb43ab5cd0aade99dd94a3c8#diff-347934b3c3deec68d1d37e86391e0f3f
- Fix:  https://github.ccs.neu.edu/CS4500-F20/fritch/commit/b165f46fade8b2c91b83c45ffe6f86fb3eeaed8f#diff-347934b3c3deec68d1d37e86391e0f3fL1-R2

## GameTree
The code we wrote for the integration testing for Milestone 4 failed due to our misunderstanding
 of the use of the 'players' JSON array, resulting in incorrect serialization for output in the
  test harness.
- Commit containing integration testing code with bug: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/3c1e628103d45cbb110e3a9932c92a42771fd10e 
- Fix:  https://github.ccs.neu.edu/CS4500-F20/fritch/commit/f28ba6eb1ccca5db44ce520253d450933b3f4121
(Specifically, the changes made were to State.java and Xstate.java. The JSON tests we wrote were
 also changed to reflect the correct understanding of the requirements.)

## Strategy
The tests we wrote for the integration testing for Milestone 5 failed due to being invalid game
 states (lacking sufficient players/penguins).
 - Commit containing invalid n-in.json and n-out.json files in 5: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/31f562ab430a6d1a9ec3a776b2e092149e236664
- Fix: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/85d052d60e2fa13123c21c25f5fac1c7a85a4a95
