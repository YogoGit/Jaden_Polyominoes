export default class StatsKeeper {

    #game;
    #timeElement;
    #levelElement;
    #scoreElement;
    #rowsClearedElement;
    #piecesElement;
    #time;
    #piecesPlayed;
    #rowsCleared;
    #score;
    #nextLevelScore;
    #gameTimerId;


    constructor(game) {
        this.#game = game;
        this.#timeElement = $("#time-value")
        this.#levelElement = $("#level-value")
        this.#scoreElement = $("#score-value")
        this.#rowsClearedElement = $("#rows-value")
        this.#piecesElement = $("#pieces-value")
        this.#time = 0.0;
        this.#piecesPlayed = 0;
        this.#rowsCleared = 0;
        this.#score = 0;
        this.#nextLevelScore = 1024;
    }

    updateStatsDisplay() {
        this.#timeElement.text(Math.round(this.#time * 100) / 100);
        this.#levelElement.text(this.#game.level);
        this.#scoreElement.text(this.#score);
        this.#rowsClearedElement.text(this.#rowsCleared);
        this.#piecesElement.text(this.#piecesPlayed);
    }

    incrementPiece() {
        this.#piecesPlayed = this.#piecesPlayed + 1;
        this.#piecesElement.text(this.#piecesPlayed);
    }

    incrementTime(statsKeeper) {
        statsKeeper.#time = statsKeeper.#time + 0.1;
        statsKeeper.#timeElement.text(Math.round(statsKeeper.#time * 100) / 100);
        return;
    }

    incrementRowsCleared() {
        this.#rowsCleared = this.#rowsCleared + 1;
        this.#rowsClearedElement.text(this.#rowsCleared);
    }

    startTimer() {
        const statsKeeper = this;
        this.#gameTimerId = setInterval(function () {
            statsKeeper.incrementTime(statsKeeper)
        }, 100);
    }

    stopTimer() {
        clearInterval(this.#gameTimerId);
    }

    increaseScore(n) {
        this.#score = this.#score + n;
        if (this.#score >= this.#nextLevelScore) {
            this.#game.levelUp();
            this.#levelElement.text(this.#game.level)
            this.#nextLevelScore = this.#nextLevelScore * 2;
        }
        this.#scoreElement.text(this.#score);
    }

    scoreSoftDrop(dist) {
        while (dist-- > 0)
            this.increaseScore(1 * this.#game.level);
    }

    scoreHardDrop(dist) {
        while (dist-- > 0)
            this.increaseScore(2 * this.#game.level);
    }

    scoreRowsCleared(numRows) {
        for (let count = 0; count < numRows; count++) {
            this.incrementRowsCleared();
        }
        let points = 100 + (numRows - 1) * 300;
        this.increaseScore(points);
    }

    sendStats() {
        const stats = {
            "time": Math.round(this.#time * 100) / 100,
            "level": this.#game.level,
            "score": this.#score,
            "rows": this.#rowsCleared,
            "pieces": this.#piecesPlayed
        }
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            url: "/api/game",
            dataType: "json",
            data: JSON.stringify(stats)
        });
    }
}
