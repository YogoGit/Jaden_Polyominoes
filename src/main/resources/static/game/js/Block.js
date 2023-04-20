import Game from "./Game.js";

/**
 *  Constructs a block that may be used to form a larger piece.
 */
export default class Block {
    #left;
    #top;
    #color;
    #grid;
    #width;
    #height;

    constructor(top, left, color) {
        this.#top = top;
        this.#left = left;
        this.#color = color;
        this.#grid = undefined;
        this.#width = Game.BLOCK_WIDTH;
        this.#height = Game.BLOCK_HEIGHT;
    }

    get top() {
        return this.#top;
    }

    set top(intBlockOffset) {
        this.#top = intBlockOffset;
    }

    get left() {
        return this.#left;
    }

    set left(intBlockOffset) {
        this.#left = intBlockOffset;
    }

    get color() {
        return this.#color;
    }

    get position() {
        return {
            top: this.#top,
            left: this.#left
        }
    }

    mayStepLeft() {
        return this.#left > 0 &&
            !this.#grid.isOffLimits(
                this.#top,
                this.#left - 1
            );
    }

    mayStepRight() {
        return this.#left < this.#grid.blocksWide - 1 &&
            !this.#grid.isOffLimits(
                this.#top,
                this.#left + 1
            );
    }

    mayStepUp() {
        return this.#top > 0 &&
            !this.#grid.isOffLimits(
                this.#top - 1,
                this.#left
            );
    }

    mayStepDown() {
        return this.#top < this.#grid.blocksTall - 1 &&
            !this.#grid.isOffLimits(
                this.#top + 1,
                this.#left,
            );
    }

    forceLeft() {
        this.left = this.#left - 1;
    }

    forceRight() {
        this.left = this.#left + 1;
    }

    forceUp() {
        this.top = this.#top - 1;
    }

    forceDown() {
        this.top = this.#top + 1;
    }

    stepLeft() {
        if (this.mayStepLeft()) this.forceLeft();
    }

    stepRight() {
        if (this.mayStepRight()) this.forceRight();
    }

    stepUp() {
        if (this.mayStepUp()) this.forceUp();
    }

    stepDown() {
        if (this.mayStepDown()) this.forceDown();
    }

    moveDown(numSteps) {
        while (numSteps-- > 0)
            this.forceDown();
    }

    moveRight(numSteps) {
        while (numSteps-- > 0)
            this.forceRight();
    }

    addTo(grid) {
        this.#grid = grid;
        this.#grid.addBlock(this);
    }

    removeFrom() {
        this.#grid.removeBlock(this);
        this.#grid = undefined;
    }

    draw() {
        if (this.#grid === undefined) return;
        const ctx = this.#grid.context;
        const x = this.#left * this.#width,
            y = this.#top * this.#height;
        ctx.fillStyle = this.#color;
        ctx.fillRect(x, y, this.#width, this.#height);
    }

}

