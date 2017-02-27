# Magic Image

Welcome to the MagicImage wiki! 

This project provider method for create magic beatiful image for android platform.

Android developer can use this project for generate bitmap.

## How to Use

This project develop by gradle and has upload to jcenter repo. 

### Declaration repo.

Add repo in project `build.gradle`

```
 repositories {
        maven {
            url  "http://dl.bintray.com/quenlenliu/MagicImage" 
        }
 }

```

### Declaration dependency.

Add dependency in model `build.gradle`

`
 compile 'org.quenlen.magic:magic:1.0.5'
`

### Use in Java code.

```
Bitmap bitmap;

MagicImage.gaussianBlur(bitmap);
```

Default radius is 32, you can change this by call 

```
Bitmap bitmap;
int radius = 24;
MagicImage.gaussianBlur(bitmap, radius);
```
