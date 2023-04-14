
export default class Bag extends Array {

    /**
     *
     * @param {any} args
     */
    constructor(...args) {
        super(...args);
    }

    swap(i, j) {
        const tmp = this[i];
        this[i] = this[j];
        this[j] = tmp;
    }

    shuffle() {
        for (let n = this.length; n > 1; --n) {
            const i = Math.floor(Math.random() * n);
            const j = n - 1;
            if (i !== j) {
                this.swap(i, j);
            }
        }
    }


}