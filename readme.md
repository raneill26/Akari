# Akari (Light Up) Puzzle Game

This is a JavaFX-based implementation of the game **Akari**, a well known logic puzzle game with the goal of placing light bulbs to illuminate all corridors while adhering to certain rules.

---

## How the Game Works

**Rules:**
- Place light bulbs in white (corridor) cells
- Each bulb lights up its row and column until a wall blocks the light
- No two bulbs may shine towards each other
- Numbered black cells (Clue cells) must have the exact number of bulbs specified adjacent to them (not diagonal)
- The puzzle is solved when:
  - All white cells are lit
  - No bulbs light each other
  - All clues are satisfied

---

## Project Structure

This project uses the Model-View-Controller (MVC) architecture:

### Model
- `ModelImpl`, `PuzzleImpl`, `PuzzleLibraryImpl`: Game logic, puzzle data, and game state
- `CellType`: Enum for cell types (wall, clue, corridor)
- `ModelObserver`: Observer pattern for automatic UI updates

### View (JavaFX UI)
- `BoardView`: Displays the puzzle grid with interactive buttons
- `TitleView`: Shows the "Akari" game title
- `WinningView`: Displays a "Winner!" message when the puzzle is solved
- `MainView`: Manages layout and view switching
- `AppLauncher`: Initializes the game and loads sample puzzles

### Controller
- `ControllerImpl`: Handles user interactions (placing lamps, switching puzzles, resetting)

---

## How to Run the Game

### Prerequisites
- Java 11+ (JavaFX compatible)
- JavaFX SDK
- IntelliJ IDEA or VS Code recommended

---

### Running using IDE

1. **Clone the repository:**
   ```bash
   git clone git@github.com:raneill26/Akari.git
   cd akari

2. **Import as a Java project in your IDE.**

3. **Add JavaFX to your project setup:**
  In IntelliJ: Go to Project Structure → Libraries → Add JavaFX SDK.

4. **Run Main.java**

---

## Controls

| Action             |                                  |
|--------------------|----------------------------------|
| Place/remove bulb  | Click on a white (corridor) cell |
| Next puzzle        | Click `Next Puzzle`              |
| Previous puzzle    | Click `Previous Puzzle`          |
| Random puzzle      | Click `Random Puzzle`            |
| Reset puzzle       | Click `Reset Puzzle`             |

---

## Sample Puzzles

Five sample puzzles are given in the file `SamplePuzzles.java`. These vary in size and difficulty. You can modify or add your own puzzles by changing the arrays in that file.

---

## Future Improvements

- Timer or move counter  
- Use of sound 
- Keyboard navigation  
- Improved visual effects (glow, win animations)

---

## License

MIT License

---

## Author

Ryan N. – UNC Chapel Hill Computer Science Student