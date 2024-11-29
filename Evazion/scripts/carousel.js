constructor (element) {
    this.element = element;
    this.currentItem = 0;
}

next() {
    this.gotoItem(this.currentItem + 1);
}

prev() {
    this.gotoItem(this.currentItem - 1);
}

goToItem(index) {
    if (index < 0) {
        index = this.items.length - this.visible;
    }
    else if (index >= this.items.length || ((this.items[this.currentItem + this.visible] === undefined) && index > this.currentItem)) {
        index = 0;
    }
    let translateX = index * -100 / this.items.length;
    this.container.style.transform = 'translate3d(' + translateX + '%, 0, 0)';
    this.currentItem = index; // Update index de l'image
}

document.addEventListener("DOMContentLoaded", function () {
    let carousel = document.getElementById("carousel1");
    new Carousel(carousel, 1);
});