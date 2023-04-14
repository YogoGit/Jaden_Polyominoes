import GameController from "./GameController.js";
import PieceManager from "./PieceManager.js";
import StatsKeeper from "./StatsKeeper.js";
import Grid from "./Grid.js";


/**
 *  Creates an object representing an -animo game
 *  with methods that may be used to play the game.
 */

export default class Game {

    static PIECE_MAX_BLOCKS_WIDE = 6;
    static PIECE_MAX_BLOCKS_TALL = 3;
    static GRID_BLOCKS_WIDE = 10;
    static GRID_BLOCKS_TALL = 20;
    static ON_HOLD_BLOCKS_WIDE = 6;
    static ON_HOLD_BLOCKS_TALL = 3;
    static QUEUE_BLOCKS_WIDE = 6;
    static QUEUE_BLOCKS_TALL = 20;
    static BLOCK_WIDTH = 32;
    static BLOCK_HEIGHT = 32;

    #controller;
    #manager;
    #statsKeeper;
    #grid;
    #onDeckScreen;
    #messageElement;
    #currentLevel;
    #gravityTimerId;
    #pacingTimerId;
    #paceRight;
    #gameIsReady;
    #gameIsStarted;
    #gameIsOver;
    #gameIsPaused;
    #currentPiece;
    #onDeckPiece;
    #holdPiece;
    #removedPiece;
    #holdTurn;
    #gravityDelay;
    #pacingDelay;


    constructor() {
        this.#controller = new GameController(this);
        this.#manager = new PieceManager(this);
        this.#statsKeeper = new StatsKeeper(this);
        this.#grid = new Grid(this, "grid", Game.GRID_BLOCKS_WIDE, Game.GRID_BLOCKS_TALL);
        this.#messageElement = $("#game-message")
        this.#currentLevel = 1;
        this.#paceRight = true;
        this.#gameIsReady = false;
        this.#gameIsStarted = false;
        this.#gameIsOver = false;
        this.#currentPiece = undefined;
        this.#onDeckPiece = undefined;
        this.#holdPiece = undefined;
        this.#removedPiece = undefined;
        this.#holdTurn = false;
        this.#gravityDelay = 500;
        this.#pacingDelay = 200;
        this.initialize();
        Object.freeze(this);
    }

    get level() {
        return this.#currentLevel;
    }

    controller() {
        return this.#controller;
    }

    isOver() {
        return this.#gameIsOver;
    }

    isStarted() {
        return this.#gameIsStarted;
    }

    isReady() {
        return this.#gameIsReady;
    }

    isPaused() {
        return this.#gameIsPaused;
    }

    levelUp() {
        this.#currentLevel = this.#currentLevel + 1;
        this.turnOnGravity();
    }


    moveToBottom() {
        if (!this.mayMove()) return;
        while (this.#currentPiece.mayStepDown()) {
            this.#currentPiece.stepDown();
            this.#statsKeeper.scoreHardDrop(1);
        }
        this.lockCurrentPiece();
        this.getNextPiece();
    }

    moveDownOneStep() {
        if (!this.mayMove()) return;
        if (this.#currentPiece.mayStepDown()) {
            this.#currentPiece.stepDown();
            this.#statsKeeper.scoreSoftDrop(1);
        } else {
            this.lockCurrentPiece();
            this.getNextPiece();
        }
    }

    moveLeftOneStep() {
        if (!this.mayMove()) return;
        this.#currentPiece.stepLeft();
    }

    moveRightOneStep() {
        if (!this.mayMove()) return;
        this.#currentPiece.stepRight();
    }

    hold() {
        if (this.#holdTurn = true) {
            if (this.#holdPiece !== undefined) {
                const nextPiece = this.#manager.getFromOnHold();
                console.assert(nextPiece === this.#holdPiece);
                this.#addToGrid(nextPiece);
                this.#holdPiece = this.#currentPiece;
                this.#manager.putOnHold(this.#holdPiece);
                this.#currentPiece = nextPiece;
            } else {
                this.#holdPiece = this.#currentPiece;
                this.#manager.putOnHold(this.#holdPiece);
                this.getNextPiece();
            }
        }
    }

    resume() {
        if (this.#gameIsOver) return false;
        if (!this.#gameIsStarted) this.start();
        if (!this.#gameIsPaused) return false;
        this.#gameIsPaused = false;
        this.#messageElement.text("");
        return true;
    }

    restart() {
        this.#end();
        this.#manager.clear();
        this.#statsKeeper.stopTimer();
        this.#manager = new PieceManager(this);
        this.#statsKeeper = new StatsKeeper(this);
        this.#grid = new Grid(this, "grid", Game.GRID_BLOCKS_WIDE, Game.GRID_BLOCKS_TALL);
        this.#currentLevel = 1;
        this.#paceRight = true;
        this.#gameIsReady = false;
        this.#gameIsStarted = false;
        this.#gameIsOver = false;
        this.#currentPiece = undefined;
        this.#onDeckPiece = undefined;
        this.#holdPiece = undefined;
        this.#removedPiece = undefined;
        this.#holdTurn = false;
        this.#gravityDelay = 500;
        this.#pacingDelay = 200;
        this.initialize();
    }

    rotateCounterClockwise() {
        this.#currentPiece.configureClockwise();
        this.#currentPiece.rotate();
    }
    rotateCounterclockwise() {
        this.#currentPiece.configureCounterclockwise();
        this.#currentPiece.rotate();
    }

    togglePaused() {
        if (this.#gameIsPaused) this.resume();
        else this.pause();
    }

    #addToGrid(piece) {
        piece.addTo(this.#grid, 0, this.#grid.blocksWide - piece.getBlocksWide());
    }

    #end() {
        this.#gameIsOver = true;
        this.#statsKeeper.stopTimer();
        clearInterval(this.#gravityTimerId);
        clearInterval(this.#pacingTimerId);
        this.#statsKeeper.sendStats();
        this.#messageElement.text("Game over press 'r' to restart")
    }

    getNextPiece() {
        this.#holdTurn = false;
        this.#currentPiece = this.#onDeckPiece;
        if (this.#currentPiece.mayStepDown()) {
            this.putNextPieceOnDeck();
            this.#statsKeeper.incrementPiece();
            return;
        }
        this.#end();
    }

    initialize() {
        this.#manager.fillQueue();
        this.putNextPieceOnDeck();
        this.#gameIsReady = true;
        this.#statsKeeper.updateStatsDisplay();
        const game = this;
        this.#pacingTimerId = setInterval(function () {
            game.makeOnDeckPiecePace(game)
        }, this.#pacingDelay);
        this.#messageElement.text("Press any key to start")
    }

    lockCurrentPiece() {
        this.#currentPiece.lock();
        const numRowsCleared = this.#grid.clearFullRows();
        if (numRowsCleared > 0) {
            this.#statsKeeper.scoreRowsCleared(numRowsCleared);
        }
    }

    mayMove() {
        return this.#gameIsStarted && !this.#gameIsOver && !this.#gameIsPaused;
    }

    makeOnDeckPiecePace(game) {
        if (game.#gameIsPaused) return;
        const l = game.#onDeckPiece.getLeft();
        const w = game.#onDeckPiece.getBlocksWide();
        if (l === 0) game.#paceRight = true;
        else if (l + w >= Game.GRID_BLOCKS_WIDE) game.#paceRight = false;
        if (game.#paceRight === true) game.#onDeckPiece.stepRight();
        else game.#onDeckPiece.stepLeft();
    }

    makePieceFallOneStep(game) {
        if (game.#gameIsPaused) return;
        if (game.#currentPiece.mayStepDown()) {
            game.#currentPiece.stepDown();
        } else {
            game.lockCurrentPiece();
            game.getNextPiece()
        }
    }

    pause() {
        this.#gameIsPaused = true;
        this.#statsKeeper.stopTimer();
        this.#messageElement.text("Paused")
    }

    putNextPieceOnDeck() {
        this.#onDeckPiece = this.#manager.getFromQueue();
        this.#addToGrid(this.#onDeckPiece);
    }

    start() {
        this.#gameIsStarted = true;
        this.#messageElement.text("");
        this.getNextPiece();
        this.#statsKeeper.startTimer();
        this.turnOnGravity();
    }

    turnOnGravity() {
        if (this.#gravityTimerId !== undefined) {
            clearInterval(this.#gravityTimerId);
        }
        const delay = (this.#currentLevel < 10) ? this.#gravityDelay - 50 * (this.#currentLevel - 1) : 50;
        const game = this;
        this.#gravityTimerId = setInterval(function () {
            game.makePieceFallOneStep(game)
        }, delay);
    }

}