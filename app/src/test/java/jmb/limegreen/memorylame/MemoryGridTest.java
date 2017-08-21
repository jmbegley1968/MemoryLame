package jmb.limegreen.memorylame;

import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jmb.limegreen.memorylame.api.MemoryCell;
import jmb.limegreen.memorylame.api.MemoryGrid;
import jmb.limegreen.memorylame.api.SelectionResult;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MemoryGridTest {

    @Test
    public void testAutoAssignConstructor() {
        int gridSize = 4;
        int valueSize = gridSize / 2;
        List<Integer> keys = new ArrayList<>();
        for (int i=0; i<gridSize; i++) {
            keys.add(i);
        }
        List<Integer> values = new ArrayList<>();
        for (int i=0; i<valueSize; i++) {
            values.add(i);
        }
        MemoryGrid<Integer, Integer> grid = new MemoryGrid<>(keys, values);
        Map<Integer, MemoryCell<Integer>> cells = grid.getCells();
        // Length
        assertEquals(gridSize, cells.size());
        // Assert each value is in correct range represented twice
        Map<Integer, Integer> valueCount = new HashMap<>();
        for(int i=0; i<gridSize; i++) {
            int cellValue = cells.get(i).value;
            if (!valueCount.containsKey(cellValue)) {
                valueCount.put(cellValue, 0);
            }
            valueCount.put(cellValue, valueCount.get(cellValue)+1);
            assertTrue(cells.get(i).value <= valueSize);
        }
        for(int i=0; i<valueSize; i++) {
            assertEquals(2, (int)valueCount.get(i));
        }
    }

    @Test
    public void test4CellQuickWin() {
        // Grid
        Map<Integer, MemoryCell<Integer>> cells = new HashMap<>();
        cells.put(0, new MemoryCell<>(1));
        cells.put(1, new MemoryCell<>(0));
        cells.put(2, new MemoryCell<>(1));
        cells.put(3, new MemoryCell<>(0));
        MemoryGrid<Integer, Integer> grid = new MemoryGrid<>(cells);
        // Turn 2 and 0 (match)
        int turn1Value = grid.revealCell(2).cellValue;
        assertEquals(1, turn1Value);
        SelectionResult result = grid.revealCell(0);
        assertEquals(true, result.selectionSuccess);
        assertEquals(false, result.gridSuccess);
        assertEquals(0, result.currentScore);
        assertEquals(1, result.cellValue);
        // Turn 1 and 3 (final match)
        turn1Value = grid.revealCell(1).cellValue;
        assertEquals(0, turn1Value);
        result = grid.revealCell(3);
        assertEquals(true, result.selectionSuccess);
        assertEquals(true, result.gridSuccess);
        assertEquals(0, result.currentScore);
        assertEquals(0, result.cellValue);
    }

    @Test
    public void test4CellWin() {
        // Grid
        Map<Integer, MemoryCell<Integer>> cells = new HashMap<>();
        cells.put(0, new MemoryCell<>(1));
        cells.put(1, new MemoryCell<>(0));
        cells.put(2, new MemoryCell<>(1));
        cells.put(3, new MemoryCell<>(0));
        MemoryGrid<Integer, Integer> grid = new MemoryGrid<>(cells);
        // Turn 0 and 1 (mismatch)
        int turn1Value = grid.revealCell(0).cellValue;
        assertEquals(1, turn1Value);
        SelectionResult result = grid.revealCell(1);
        assertEquals(false, result.selectionSuccess);
        assertEquals(false, result.gridSuccess);
        assertEquals(0, result.currentScore);
        assertEquals(0, result.cellValue);
        // Turn 2 and 0 (match)
        turn1Value = grid.revealCell(2).cellValue;
        assertEquals(1, turn1Value);
        result = grid.revealCell(0);
        assertEquals(true, result.selectionSuccess);
        assertEquals(false, result.gridSuccess);
        assertEquals(0, result.currentScore);
        assertEquals(1, result.cellValue);
        // Turn 1 and 3 (final match)
        turn1Value = grid.revealCell(1).cellValue;
        assertEquals(0, turn1Value);
        result = grid.revealCell(3);
        assertEquals(true, result.selectionSuccess);
        assertEquals(true, result.gridSuccess);
        assertEquals(0, result.currentScore);
        assertEquals(0, result.cellValue);
    }

    @Test
    public void test4Cell1Mistake() {
        // Grid
        Map<Integer, MemoryCell<Integer>> cells = new HashMap<>();
        cells.put(0, new MemoryCell<>(1));
        cells.put(1, new MemoryCell<>(0));
        cells.put(2, new MemoryCell<>(1));
        cells.put(3, new MemoryCell<>(0));
        MemoryGrid<Integer, Integer> grid = new MemoryGrid<>(cells);
        // Turn 0 and 1 (mismatch)
        int turn1Value = grid.revealCell(0).cellValue;
        assertEquals(1, turn1Value);
        SelectionResult result = grid.revealCell(1);
        assertEquals(false, result.selectionSuccess);
        assertEquals(false, result.gridSuccess);
        assertEquals(0, result.currentScore);
        assertEquals(0, result.cellValue);
        // Turn 2 and 1 (mismatch)
        turn1Value = grid.revealCell(2).cellValue;
        assertEquals(1, turn1Value);
        result = grid.revealCell(1);
        assertEquals(false, result.selectionSuccess);
        assertEquals(false, result.gridSuccess);
        assertEquals(-1, result.currentScore);
        assertEquals(0, result.cellValue);
        // Turn 1 and 3 (match)
        turn1Value = grid.revealCell(1).cellValue;
        assertEquals(0, turn1Value);
        result = grid.revealCell(3);
        assertEquals(true, result.selectionSuccess);
        assertEquals(false, result.gridSuccess);
        assertEquals(-1, result.currentScore);
        assertEquals(0, result.cellValue);
        // Turn 2 and 0 (final match)
        turn1Value = grid.revealCell(2).cellValue;
        assertEquals(1, turn1Value);
        result = grid.revealCell(0);
        assertEquals(true, result.selectionSuccess);
        assertEquals(true, result.gridSuccess);
        assertEquals(-1, result.currentScore);
        assertEquals(1, result.cellValue);
    }

    @Test
    public void test6Cell2Mistakes() {
        // Grid
        Map<Integer, MemoryCell<Integer>> cells = new HashMap<>();
        cells.put(0, new MemoryCell<>(1));
        cells.put(1, new MemoryCell<>(0));
        cells.put(2, new MemoryCell<>(1));
        cells.put(3, new MemoryCell<>(0));
        cells.put(4, new MemoryCell<>(2));
        cells.put(5, new MemoryCell<>(2));
        MemoryGrid<Integer, Integer> grid = new MemoryGrid<>(cells);
        // Turn 0 and 1 (mismatch)
        int turn1Value = grid.revealCell(0).cellValue;
        assertEquals(1, turn1Value);
        SelectionResult result = grid.revealCell(1);
        assertEquals(false, result.selectionSuccess);
        assertEquals(false, result.gridSuccess);
        assertEquals(0, result.currentScore);
        assertEquals(0, result.cellValue);
        // Turn 2 and 1 (mismatch)
        turn1Value = grid.revealCell(2).cellValue;
        assertEquals(1, turn1Value);
        result = grid.revealCell(1);
        assertEquals(false, result.selectionSuccess);
        assertEquals(false, result.gridSuccess);
        assertEquals(-1, result.currentScore);
        assertEquals(0, result.cellValue);
        // Turn 1 and 3 (match)
        turn1Value = grid.revealCell(1).cellValue;
        assertEquals(0, turn1Value);
        result = grid.revealCell(3);
        assertEquals(true, result.selectionSuccess);
        assertEquals(false, result.gridSuccess);
        assertEquals(-1, result.currentScore);
        assertEquals(0, result.cellValue);
        // Turn 2 and 4 (mismatch)
        turn1Value = grid.revealCell(2).cellValue;
        assertEquals(1, turn1Value);
        result = grid.revealCell(4);
        assertEquals(false, result.selectionSuccess);
        assertEquals(false, result.gridSuccess);
        assertEquals(-2, result.currentScore);
        assertEquals(2, result.cellValue);
        // Turn 2 and 0 (match)
        turn1Value = grid.revealCell(2).cellValue;
        assertEquals(1, turn1Value);
        result = grid.revealCell(0);
        assertEquals(true, result.selectionSuccess);
        assertEquals(false, result.gridSuccess);
        assertEquals(-2, result.currentScore);
        assertEquals(1, result.cellValue);
        // Turn 4 and 5 (final match)
        turn1Value = grid.revealCell(4).cellValue;
        assertEquals(2, turn1Value);
        result = grid.revealCell(5);
        assertEquals(true, result.selectionSuccess);
        assertEquals(true, result.gridSuccess);
        assertEquals(-2, result.currentScore);
        assertEquals(2, result.cellValue);
    }
}