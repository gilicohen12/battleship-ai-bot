Changes made to PA03

- Refactored APlayer class to AbstractPlayer for checkstyle
- Created Direction enum in order to represent the direction of ship, instead of the 
original boolean. Updated the Ship class and AbstractPlayer methods accordingly
- Created a GameResult enum in the PA03 model to make it compatible with the Jsons
- Added a getType() method in the Ship class
- Added a getDirection() method in the Ship class