window.onload = () => {
    setTimeout(() => {
        const notification = document.querySelector('.notification');
        if (notification) {
            notification.style.display = 'none';
        }
    }, 2500);
};
