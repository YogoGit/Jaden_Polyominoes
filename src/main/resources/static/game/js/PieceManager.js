import polyominoes from "../json/pieces.json" assert {type: "json"};
import Block from "./Block.js";
import Polyomino from "./Polyomino.js";
import Grid from "./Grid.js";
import Game from "./Game.js";

export default class PieceManager {

    #game;
    #onHold;
    #queue;
    #bagOfPieces;
    #queuedPieces;
    #pieceOnHold;

    constructor(game) {
        this.#game = game;
        this.#onHold = new Grid(this.#game, "on-hold", Game.ON_HOLD_BLOCKS_WIDE, Game.ON_HOLD_BLOCKS_TALL);
        this.#queue = new Grid(this.#game, "queue", Game.QUEUE_BLOCKS_WIDE, Game.QUEUE_BLOCKS_TALL);
        this.#bagOfPieces = [];
        this.#queuedPieces = [];
        this.#pieceOnHold = undefined;
    }

    clear() {
        this.#onHold.clear();
    }

    getPieces() {
        const piecesList = [];
        for (let k in polyominoes) {
            const pieceType = polyominoes[k];
            const pivotIndex = pieceType["pivotIndex"];
            const minLevel = pieceType["minLevel"];
            const pieces = pieceType["pieces"];
            pieces.forEach(function (piece) {
                const color = piece["color"];
                const top = piece["top"];
                const left = piece["left"];
                const numBlocks = Math.min(top.length, left.length);
                const blocks = [];
                for (let i = 0; i < numBlocks; i++) {
                    blocks.push(new Block(top[i], left[i], color));
                }
                piecesList.push(new Polyomino(blocks, pivotIndex, minLevel));
            });
        }
        return piecesList;
    }

    getRandomPiece() {
        const pieces = this.getPieces();
        const i = Math.floor(Math.random() * pieces.length);
        return pieces[i];
    }

    fillBag() {
        const level = this.#game.level;
        const bagCapacity = level * this.getPieces().length;
        while (this.#bagOfPieces.length < bagCapacity) {
            const piece = this.getRandomPiece()
            if (piece != undefined && piece.minLevel <= level) {
                this.#bagOfPieces.push(piece);
            }
        }
        this.#shuffle()
    }

    #swap(i, j) {
        const tmp = this.#bagOfPieces[i];
        this.#bagOfPieces[i] = this.#bagOfPieces[j];
        this.#bagOfPieces[j] = tmp;
    }

    #shuffle() {
        for (let n = this.#bagOfPieces.length; n > 1; --n) {
            const i = Math.floor(Math.random() * n);
            const j = n - 1;
            if (i !== j) {
                this.#swap(i, j);
            }
        }
    }

    getFromBag() {
        if (this.#bagOfPieces.length == 0) {
            this.fillBag();
        }

        return this.#bagOfPieces.shift();
    }

    isQueueFull() {
        const n = this.#queuedPieces.length;
        if (n === 0) return false;
        const lastPiece = this.#queuedPieces[n - 1];
        const used = lastPiece.getTop() + lastPiece.getBlocksTall();
        return this.#queue.blocksTall - used < Game.PIECE_MAX_BLOCKS_TALL;
    }

    fillQueue() {
        this.shiftQueuedPieces();
        while (!this.isQueueFull()) {
            this.addToQueue();
        }
    }

    getFromOnHold() {
        if (this.#pieceOnHold === undefined) return;
        this.#pieceOnHold.removeFrom();
        return this.#pieceOnHold;
    }

    putOnHold(piece) {
        this.#pieceOnHold = piece;
        this.#pieceOnHold.addTo(this.#onHold, 0, 0)
        this.#pieceOnHold.draw();
    }

    addToQueue() {
        const nextPiece = this.getFromBag();
        nextPiece.addTo(
            this.#queue,
            this.#queue.blocksTall - nextPiece.getBlocksTall(),
            0
        );

        this.#queuedPieces.push(nextPiece);
        this.shiftQueuedPieces();
    }

    removeFromQueue() {
        if (this.#queuedPieces.length === 0) return;
        const removedPiece = this.#queuedPieces.shift();
        removedPiece.unlock();
        removedPiece.removeFrom();
        this.shiftQueuedPieces();
        return removedPiece;
    }

    shiftQueuedPieces() {
        this.#queuedPieces.forEach(function (piece) {
            if (piece.isLocked) {
                piece.unlock();
            }
            while (piece.mayStepUp()) {
                piece.stepUp();
            }
            while (piece.mayStepLeft()) {
                piece.stepLeft();
            }
            piece.lock();
        });
    }

    getFromQueue() {
        this.fillQueue();
        const nextPiece = this.removeFromQueue();
        this.fillQueue();
        return nextPiece;
    }
}

