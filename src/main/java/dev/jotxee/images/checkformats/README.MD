# Explicación de la Clase `DiscardImagesByFormat`

### Método `getValidImageUrls(List<String> imageUrls)`

Este método toma una lista de URLs de imágenes y utiliza el método `stream()` para filtrar las URL de imágenes válidas utilizando el método `isValidImage()` que verificamos a continuación. Finalmente, los resultados filtrados se recopilan en una lista y se devuelven.

### Método `isValidImage(String imageUrl)`

Este método verifica si una imagen es válida al intentar obtener su contenido mediante una solicitud HTTP. Luego, utiliza el método `getImageFormat()` para obtener el formato de la imagen. Si el formato es "Desconocido", se imprime un mensaje y se retorna `false`. De lo contrario, se verifica si el formato es JPEG, WebP o PNG, y se retorna `true` o `false` en consecuencia.

### Método `getImageFormat(byte[] imageBytes)`

Este método utiliza un `switch` para determinar el formato de la imagen basado en su firma de bytes. Si la firma coincide con alguna de las firmas predefinidas, se devuelve el formato correspondiente (JPEG, WebP o PNG). Si no coincide con ninguna firma, se devuelve "Desconocido".

### Método `bytesToHex(byte[] bytes)`

Este método toma un array de bytes y convierte cada byte en su representación hexadecimal de dos caracteres. Luego, se concatena para formar una cadena hexadecimal.

Estas son las partes clave de la clase `DiscardImagesByFormat` y su funcionamiento en detalle.
