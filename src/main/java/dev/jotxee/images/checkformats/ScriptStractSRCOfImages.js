// ULR de extracción de ejempplo: https://shop.mango.com/es/bebe-nino/camisetas-manga-larga/camiseta-manga-larga-estampada_57044770.html?c=02
let imageUrls = Array.from(document.querySelectorAll('.image-btn img')).map(image => 'https://'+ image.getAttribute('src').replace(/^\/\//, ''));
