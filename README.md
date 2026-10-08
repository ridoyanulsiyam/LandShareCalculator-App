# LandShareCalculator

বাংলা ভাষাভিত্তিক ঐতিহ্যবাহী খতিয়ানের আনা-গণ্ডা-কড়া-ক্রান্তি-তিল অংশ হিসাবের শিক্ষামূলক Android app।

## Final calculation rules

- আনা: ১–১৬
- গণ্ডা: ১–১৯
- কড়া: ১–৩
- ক্রান্তি: ১–২
- তিল: ১–১৯

Carry rules:

- ১৬ আনা = ১ পূর্ণ অংশ
- ২০ গণ্ডা = ১ আনা
- ৪ কড়া = ১ গণ্ডা
- ৩ ক্রান্তি = ১ কড়া
- ২০ তিল = ১ ক্রান্তি

একটি পূর্ণ অংশ = ৭৬,৮০০ তিল।

## UI and typography

- Main Bengali UI uses the bundled `Kalpurush.ttf`.
- Developer credit uses Android default Roboto.
- Bengali numerals are used throughout the calculation UI and results.
- Home screen follows the locked three-button reference layout: orange symbol guide, blue calculation button and red practical policy button, with no extra icons or arrows.
- Home background is off-white with premium gradient buttons and a subtle glass/highlight treatment.
- Home screen, symbol guide, calculation screen, practical policy screen, developer credit and launcher resources are included.

## Build

The project targets Android SDK 36, uses Android Gradle Plugin 8.9.2 and Gradle 8.11.1.

`gradlew` and `gradlew.bat` can bootstrap Gradle 8.11.1 if Gradle is not already installed locally. GitHub Actions also builds the debug APK automatically on pushes to `main` and pull requests.

## Important

This is an educational calculation tool. Verify important land-related decisions against the relevant records and qualified authorities.
