## Self-Evaluation Form for Milestone 7

Please respond to the following items with

1. the item in your `todo` file that addresses the points below.
    It is possible that you had "perfect" data definitions/interpretations
    (purpose statement, unit tests, etc) and/or responded to feedback in a 
    timely manner. In that case, explain why you didn't have to add this to
    your `todo` list.

2. a link to a git commit (or set of commits) and/or git diffs the resolve
   bugs/implement rewrites: 

These questions are taken from the rubric and represent some of the most
critical elements of the project, though by no means all of them.

(No, not even your sw arch. delivers perfect code.)

### Board

- a data definition and an interpretation for the game _board_
    - To-do list item: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/16b2baa63d4a3eb62959f07556d1cae6f6a86bcd#diff-fffda276c12b3dbca7ad810a9a8364e5R10-R15
    - Resolution links:
        - ASCII diagram for better interpretation of a Board: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/378bd53821f9d13a91cc8eba1cc8ba20ce10e0c7#diff-036ecebb80e1c70a2eef34e0122ff3e4L11-R35
        - Argument checking in constructor for a Board: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/cacf65fb490494eb31c7ff107877ee9f4b76bdab#diff-036ecebb80e1c70a2eef34e0122ff3e4R32-R73
        - Documentation about nontermination for setOneFishTiles, used in constructor: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/22fb207c57e633642f84fe1fdced46bb657dd15e#diff-ac9d71ba0e24b5fae062c12740d1fde7L172-R175

- a purpose statement for the "reachable tiles" functionality on the board representation
    - To-do list item: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/16b2baa63d4a3eb62959f07556d1cae6f6a86bcd#diff-fffda276c12b3dbca7ad810a9a8364e5R20-R21
    - Resolution link: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/79cff9ad116cca02af505ae2a70857f54d9743c8#diff-fedce09cfce4714634a2e92d5140ecd7L10-R20

- two unit tests for the "reachable tiles" functionality
    - We did not add a to-do list item for the unit tests of the reachable tiles (for us, called getValidMoves) functionality due to missing it when going over all of the received feedback. However, unit tests had been added in a prior response to feedback on the unit tests having been insufficient, with 2 additional tests of the functionality being added to the testing method, shown in the diff link below.
    - Resolution link: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/cacf65fb490494eb31c7ff107877ee9f4b76bdab#diff-5b3d6b973c9f5e7b8d4d81d93f030c05R73-R92

### Game States 


- a data definition and an interpretation for the game _state_
    - To-do list item: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/773e8d011b9426756f3ee53554863aeb3161ca12#diff-fffda276c12b3dbca7ad810a9a8364e5R20-R24
    - Resolution link (for multiple sub-bullet points): https://github.ccs.neu.edu/CS4500-F20/fritch/commit/951cdacfdd7788add8127197b239e7e5c684c76d#diff-9d435e785720105cf495c1843387619aL21-R28

- a purpose statement for the "take turn" functionality on states
    - We did not add this as a to-do list item as we never received feedback on the purpose statement indicating it was incorrect, and we felt it was sufficiently documented to continue using it as it was.


- two unit tests for the "take turn" functionality 
    - To-do list item: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/773e8d011b9426756f3ee53554863aeb3161ca12#diff-fffda276c12b3dbca7ad810a9a8364e5R25
    - Resolution link (accidentally used master as the commit branch for the link in reworked.md; correct link is here): https://github.ccs.neu.edu/CS4500-F20/fritch/commit/07ae8edfa022cdd01ea47ee70609b0001f21c011#diff-06998b8e00f59631d78518870f9dc261R166-R213
    - This commit only adds one unit test for the repeated use-case for turn taking functionality. After this addition, there are two unit tests for moving functionality as well as several tests confirming it creates exceptions where expected.

### Trees and Strategies


- a data definition including an interpretation for _tree_ that represent entire games
    - To-do list item: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/773e8d011b9426756f3ee53554863aeb3161ca12#diff-fffda276c12b3dbca7ad810a9a8364e5R36-R43
    - Resolution link: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/f6835672e803c21ccc1b5c74cc23c587547b919b#diff-103ef51fda7a73d4f8b181b6806d415dR13-R37

- a purpose statement for the "maximin strategy" functionality on trees
    - To-do list item (needs addition to documentation): https://github.ccs.neu.edu/CS4500-F20/fritch/commit/773e8d011b9426756f3ee53554863aeb3161ca12#diff-fffda276c12b3dbca7ad810a9a8364e5R65-R67
    - Resolution link: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/c6270277a97a9c3d1449fd71207f769f5703213d#diff-fe94b8b2dcabdee1fe6fef3473e370ccL19-R22

- two unit tests for the "maximin" functionality 
    -  We did not add unit tests as a to-do list item as we never received feedback indicating we needed additional tests. This thought was further reinforced for us, since we discovered issues with integration testing for this functionality were to do with the serialization/deserialization and the underlying functionality gave proper results when fixes to JSON handling were made. We did break large tests down into what we felt was a complete set of tests, however, and this abstraction of tests is linked below.
    - Existing/cleanup of unit tests: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/29180e950f3e4cf8ae3cb61d972552b7647510f6#diff-8fc2b0eb01097ccaba66cc2e305cb637R178-R245


### General Issues

Point to at least two of the following three points of remediation: 


- the replacement of `null` for the representation of holes with an actual representation 
    - To-do list item: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/81efe2d95f958b0c23a5a202caccfbd286f5047b#diff-fffda276c12b3dbca7ad810a9a8364e5R8-R11
    - Resolution link: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/bd771b0dde4b8a09a9d8b0c09c6088f3b35b53b8#diff-1efc0a0b264deae6b06270fb0d16cfd9R18-R49
    - This is a very large commit that was performed after a code walk of the tiles & board milestone, and the relevant section (removal of TileStatus field, other interpretation changes) has been highlighted. We didn't literally use `null` as our representation for a lack of a tile, but we felt that our previous handling (an enum indicating either a penguin color of a penguin on the tile, no tile, or a tile with no occupant) was incorrect enough for this point of remediation. 

- one name refactoring that replaces a misleading name with a self-explanatory name
    - To-do list item: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/773e8d011b9426756f3ee53554863aeb3161ca12#diff-fffda276c12b3dbca7ad810a9a8364e5R46-R48
    - Resolution link (GameTree->GameTreeNode, IPlayer->IPlayerComponent): https://github.ccs.neu.edu/CS4500-F20/fritch/commit/7231bd1c1b359839ab9d4d8d3553124ec9a91388
    - The renaming of IPlayer to IPlayerComponent is incorrectly categorized under the GameTree section in the to-do list and the reworked file due to mistakes made when reorganizing from changes-by-type-of-change to changes-by-module. 

- a "debugging session" starting from a failed integration test:
  - the failed integration test
  - its translation into a unit test (or several unit tests)
  - its fix
  - bonus: deriving additional unit tests from the initial ones 


### Bonus

Explain your favorite "debt removal" action via a paragraph with
supporting evidence (i.e. citations to git commit links, todo, `bug.md`
and/or `reworked.md`).

Looking through the actions performed in our to-do list, our favorite debt removal was probably the very early changes regarding the Tile and the Board, most of which can be found in this commit: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/bd771b0dde4b8a09a9d8b0c09c6088f3b35b53b8. 

This commit made a lot of interpretation-based changes, such as removing the notion of position or occupant from Tile objects (seen in this resolution: https://github.ccs.neu.edu/CS4500-F20/fritch/commit/bd771b0dde4b8a09a9d8b0c09c6088f3b35b53b8#diff-1efc0a0b264deae6b06270fb0d16cfd9R18-R49), and saw the addition of an entirely new Hole object to represent holes instead of trying to represent it with some form of a Tile. 

This commit also included changes to the Board object, which now made use of the BoardSpace interface that Holes and Tiles implemented (https://github.ccs.neu.edu/CS4500-F20/fritch/commit/bd771b0dde4b8a09a9d8b0c09c6088f3b35b53b8#diff-036ecebb80e1c70a2eef34e0122ff3e4R20-R34) as well as removing penguins from the board interpretation. 

We also added an ASCII diagram during this commit describing the visual interpretation of a Board as it corresponded to the numerical coordinates used (https://github.ccs.neu.edu/CS4500-F20/fritch/commit/378bd53821f9d13a91cc8eba1cc8ba20ce10e0c7#diff-036ecebb80e1c70a2eef34e0122ff3e4L11-R35). Additional changes made to Tile and Board can be found in their sections in `reworked.md`. Overall, these changes were very important moving forwards as they set a foundation for us to work from. Had we not fixed the changes at this point, we would have incurred much greater technical debt as we worked on top of a bad interpretation.
