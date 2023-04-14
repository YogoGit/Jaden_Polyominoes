/************************************************************************
 * E. GRID, PIECE MANAGER, ROW SWEEPER, SCOREKEEPER
 *
 *   1. Grid
 *   2. PieceManager
 *   3. RowSweeper
 *   2. Scorekeeper
 */

import Game from "./Game.js";

/**
 *  Creates a grid for a game of Polyominoes.
 */
export default class Grid {
    #game;
    #canvas;
    #ctx;
    #blocksWide;
    #blocksTall;
    #blocks;
    #lockedBlocks;

    constructor(game, canvasId, blocksWide, blocksTall) {
        this.#game = game;
        this.#canvas = document.getElementById(canvasId);
        this.#ctx = this.#canvas.getContext("2d");
        this.#blocksWide = blocksWide;
        this.#blocksTall = blocksTall;
        this.#blocks = [];
        this.#lockedBlocks = [];
    }

    get blocksWide() {
        return this.#blocksWide
    }

    get blocksTall() {
        return this.#blocksTall
    }

    get width() {
       return this.#canvas.getBoundingClientRect().width;
    }

    get height() {
       return this.#canvas.getBoundingClientRect().height
    }

    get context() {
        return this.#ctx;
    }

    clear() {
        this.#ctx.clearRect(0,0, this.width, this.height);
    }

    clearFullRows() {
        const fullRows = this.#getFullRows();
        const grid = this;
        fullRows.forEach(function(row) {
            grid.#clearRow(row);
        });
        this.redraw()
        return fullRows.length;
    }

    #clearRow(blocks) {
        const rowNum = blocks[0].top;
        const grid = this;
        blocks.forEach(function(block) {
            const i = grid.#lockedBlocks.indexOf(block);
            grid.#lockedBlocks.splice(i, 1);
            block.removeFrom()
        });
        this.#lockedBlocks.forEach(function(block) {
            if (block.top < rowNum)
                block.moveDown(1);
        });
    }

    #partitionIntoRows() {
        const rows = [];
        for (let count = this.#blocksTall; count >= 0; --count) {
            rows.push([]);
        }
        this.#lockedBlocks.forEach(function(block) {
            rows[block.top].push(block);
        });
        return rows;
    }

    #getFullRows() {
        const rows = this.#partitionIntoRows();
        const fullRows = [];
        const grid = this;
        rows.forEach(function(row) {
            if (row.length === grid.#blocksWide) {
                fullRows.push(row);
            }
        });
        return fullRows;
    }

    unlockBlock(block) {
        const i = this.#lockedBlocks.indexOf(block);
        if (i >= 0) {
            this.#lockedBlocks.splice(i, 1);
        }
    }

    lockBlock(block) {
        this.#lockedBlocks.push(block);
    }

    isOutOfBounds(top, left) {
        return (left < 0) ||
            (left > this.#blocksWide - 1) ||
            (top < 0) ||
            (top > this.#blocksTall - 1);
    }

    isOccupied(top, left) {
        const n = this.#lockedBlocks.length;
        for (let i = 0; i < n; ++i) {
            const block = this.#lockedBlocks[i];
            if (block.left === left && block.top === top)
                return true;
        }
        return false;
    }

    isOffLimits(top, left) {
        return this.isOutOfBounds(top, left) || this.isOccupied(top, left);
    }

    addBlock(block) {
        this.#blocks.push(block);
    }

    removeBlock(block) {
        const i = this.#blocks.indexOf(block);
        if (i >= 0) {
            this.#blocks.splice(i, 1);
        }
    }

    redraw() {
        this.clear();
        this.#blocks.forEach(function (block) {
            block.draw();
        });
    }



}
