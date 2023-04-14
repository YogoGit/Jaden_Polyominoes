export default class Polyomino {

    #blocks;
    #numBlocks;
    #pivotIndex;
    #pivotBlock;
    #minLevel;
    #isHorizontal;
    #isLocked;
    #grid;
    #transformationMatrix;
    
    constructor(blocks, pivotBlockIndex, minLevel) {
        this.#blocks = blocks;
        this.#numBlocks = blocks.length;
        this.#pivotIndex = pivotBlockIndex;
        this.#pivotBlock = (0 <= pivotBlockIndex && pivotBlockIndex < blocks.length-1) ? blocks[pivotBlockIndex] : blocks[0];
        this.#minLevel = minLevel;
        this.#isHorizontal = true;
        this.#isLocked = false;
        this.#grid = undefined;
        this.#transformationMatrix = [
            [0, -1],
            [1, 0]
        ];
    }

    get minLevel() {
        return this.#minLevel;
    }

    get isLocked() {
        return this.#isLocked;
    }

    get isHorizontal() {
        return this.#isHorizontal;
    }

    get blocks() {
        return this.#blocks;
    }

    get pivotIndex() {
        return this.#pivotIndex;
    }

    getLeft() {
        let min;
        min = this.#blocks[0].left;
        for (let i = 1; i < this.#numBlocks; ++i) {
            const left = this.#blocks[i].left;
            if (left < min) min = left;
        }
        return min;
    }

    getTop() {
        let min;
        min = this.#blocks[0].top;
        for (let i = 1; i < this.#numBlocks; ++i) {
            const top = this.#blocks[i].top;
            if (top < min) min = top;
        }
        return min;
    }

    getBlocksWide() {
        let min, max;
        min = max = this.#blocks[0].left;
        for (let i = 1; i < this.#numBlocks; ++i) {
            const left = this.#blocks[i].left;
            if (left < min) min = left;
            if (left > max) max = left;
        }
        return (max - min) + 1;
    }

    getBlocksTall() {
        let min, max;
        min = max = this.#blocks[0].top;
        for (let i = 1; i < this.#numBlocks; ++i) {
            const top = this.#blocks[i].top;
            if (top < min) min = top;
            if (top > max) max = top;
        }
        return (max - min) + 1;
    }

    addTo(grid, top, left) {
        this.#grid = grid;
        this.#blocks.forEach(function(block) {
            block.addTo(grid);
        });
        this.moveTo(top, left);
        this.#grid.redraw();
    }

    removeFrom() {
        this.#blocks.forEach(function (block) {
            block.removeFrom();
        });
        this.#grid.redraw();
        this.#grid = undefined;
    }

    moveTo(top, left) {
        let minLeft = Infinity,
            minTop = Infinity;
        if (!this.isHorizontal) this.rotate(true);
        this.#blocks.forEach(function(block) {
            if (block.top < minTop) minTop = block.top;
            if (block.left < minLeft) minLeft = block.left;
        });
        this.#blocks.forEach(function(block) {
            block.top = (block.top - minTop) + top;
            block.left = (block.left - minLeft) + left;
        });
    }

    mayStepRight() {
        for (let i = 0; i < this.#numBlocks; ++i) {
            if (!this.#blocks[i].mayStepRight()) return false;
        }
        return true;
    }

    mayStepLeft() {
        for (let i = 0; i < this.#numBlocks; ++i) {
            if (!this.#blocks[i].mayStepLeft()) return false;
        }
        return true;
    }

    mayStepUp() {
        for (let i = 0; i < this.#numBlocks; ++i) {
            if (!this.#blocks[i].mayStepUp()) return false;
        }
        return true;
    }

    mayStepDown() {
        for (let i = 0; i < this.#numBlocks; ++i) {
            if (!this.#blocks[i].mayStepDown()) return false;
        }
        return true;
    }

    mayRotate() {
        const piece = this;
        for (let i = 0; i < this.#blocks.length; ++i) {
            const pos = this.getRotatedPosition(this.#blocks[i]);
            if (piece.#grid.isOffLimits(pos.top, pos.left)) { return false; }
        }
        return true;
    }

    forceRight() {
        this.#blocks.forEach(function(block) {
            block.stepRight();
        });
        this.#grid.redraw();
    }

    forceLeft() {
        this.#blocks.forEach(function(block) {
            block.stepLeft();
        });
        this.#grid.redraw();
    }

    forceUp() {
        this.#blocks.forEach(function(block) {
            block.stepUp();
        });
        this.#grid.redraw();
    }

    forceDown() {
        this.#blocks.forEach(function(block) {
            block.stepDown();
        });
        this.#grid.redraw();
    }

    unlock() {
        const grid = this.#grid;
        this.#blocks.forEach(function(block) {
            grid.unlockBlock(block);
        });
        this.#isLocked = false;
    }

    lock() {
        const grid = this.#grid;
        this.#blocks.forEach(function(block) {
            grid.lockBlock(block);
        });
        this.#isLocked = true;
    }

    getRotatedPosition(block) {
        // Credit: https://www.youtube.com/watch?v=Atlr5vvdchY
        const T = this.#transformationMatrix;
        const B = [block.left, block.top];
        const P = [this.#pivotBlock.left, this.#pivotBlock.top];
        const Vr = [P[0] - B[0], P[1] - B[1]];
        const Vt = [T[0][0] * Vr[0] + T[0][1] * Vr[1],
            T[1][0] * Vr[0] + T[1][1] * Vr[1]
        ];
        return {
            top: Vt[1] + P[1],
            left: Vt[0] + P[0]
        };
    }

    forceRotate(block) {
        const pos = this.getRotatedPosition(block);
        block.top = pos.top;
        block.left = pos.left;
    }

    stepUp() {
        if (this.mayStepUp()) this.forceUp();
    }

    stepDown() {
        if (this.mayStepDown()) this.forceDown();
    }

    stepLeft() {
        if (this.mayStepLeft()) this.forceLeft();
    }

    stepRight() {
        if (this.mayStepRight()) this.forceRight();
    }

    rotate(force = false) {
        if (this.mayRotate() || force === true) {
            for(let i = 0; i < this.#blocks.length; i++) {
                this.forceRotate(this.#blocks[i])
            }
            this.#isHorizontal = !this.#isHorizontal;
        }
    }

    draw() {
        this.#blocks.forEach(function (block) {
            block.draw();
        });
    }

}