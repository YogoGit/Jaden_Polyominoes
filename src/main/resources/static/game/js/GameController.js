import KeyCodeEnum from "./KeyCodeEnum.js";
import CommandEnum from "./CommandEnum.js";

/**
 *  Creates an object that associates the key down event with
 *  the execution of game commands and exposes a press method
 *  through which the commands associated with keys on a
 *  keyboard may be issued programmatically.
 */
export default class GameController {

    #window;
    #game;
    #controlFunctions;
    #controller;

    constructor(game) {
        this.#game = game;
        this.#controlFunctions = this.#getControlFunctions();
        document.addEventListener("keydown", e => this.onKeyDown(e), true);
    }

    keyCodes = Object.freeze({
        ESCAPE: 27,
        SPACE_BAR: 32,
        LEFT_ARROW: 37,
        UP_ARROW: 38,
        RIGHT_ARROW: 39,
        DOWN_ARROW: 40,
        P: 80,
        Z: 90,
        R: 82
    });

    onKeyDown(event) {
        const key = this.parseFunctionName(event);
        this.press(key);
        if (this.#controlFunctions.hasOwnProperty(key))
            event.preventDefault();
    }

    press(key) {
        if (!this.#game.isReady()) return;
        console.log("press: " + key);
        if(!this.#game.isStarted() && key != "pause")  {
            this.#game.resume();
            return;
        }
        const f = this.#controlFunctions[key];
        if (typeof f === "function")  setTimeout(f.bind(this.#game), 5);
    }

    parseFunctionName(event) {
        const keyPress = event.key + "(" + event.keyCode + ")";
        console.log("parseFunctionName: " + keyPress);
        switch (event.keyCode) {
            case KeyCodeEnum.ESCAPE:
                return CommandEnum.PAUSE;
            case KeyCodeEnum.SPACE_BAR:
                return CommandEnum.DROP;
            case KeyCodeEnum.LEFT_ARROW:
                return CommandEnum.LEFT;
            case KeyCodeEnum.UP_ARROW:
                return CommandEnum.CLOCKWISE;
            case KeyCodeEnum.RIGHT_ARROW:
                return CommandEnum.RIGHT;
            case KeyCodeEnum.DOWN_ARROW:
                return CommandEnum.DOWN;
            case KeyCodeEnum.H:
                return CommandEnum.HOLD;
            case KeyCodeEnum.Z:
                return CommandEnum.COUNTERCLOCKWISE;
            case KeyCodeEnum.R:
                return CommandEnum.RESTART;
            default:
                return keyPress;
        }
    }

    #getControlFunctions() {
        const dictionary = {};
        dictionary[CommandEnum.PAUSE] = this.#game.togglePaused;
        dictionary[CommandEnum.HOLD] = this.#game.hold;
        dictionary[CommandEnum.DROP] = this.#game.moveToBottom;
        dictionary[CommandEnum.LEFT] = this.#game.moveLeftOneStep;
        dictionary[CommandEnum.CLOCKWISE] = this.#game.rotateCounterClockwise;
        dictionary[CommandEnum.RIGHT] = this.#game.moveRightOneStep;
        dictionary[CommandEnum.DOWN] = this.#game.moveDownOneStep;
        dictionary[CommandEnum.COUNTERCLOCKWISE] = this.#game.rotateCounterclockwise;
        dictionary[CommandEnum.RESTART] = this.#game.restart;
        return Object.freeze(dictionary);
    }

}