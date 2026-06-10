
<p align="center"><a href="https://github.com/wade-fs/qalculator"><img src="graphics/logo_shadow.svg" width="150"></a></p> 
<h1 align="center">
  Qalculate! Android 版
</h1>
<p align="center">
  <a href="README.md">English</a> | 繁體中文
</p>
<!-- description -->
<p align="center">
  <strong>~ Android 平台上強大且多功能的通用計算機 ~</strong>
    <br />
    <a href="https://github.com/wade-fs/qalculator/issues/new">回報 Bug</a>
    ·
    <a href="https://github.com/wade-fs/qalculator/issues/new">要求新功能</a>
</p>

這是強大的 <a href="https://qalculate.github.io">Qalculate!</a> 計算機在 Android 上的介面實作。它易於使用，但提供了通常僅限於複雜數學軟體包的強大功能與靈活性，以及日常所需的實用工具（如貨幣換算和百分比計算）。功能包括龐大的自訂函數庫、單位計算與轉換、符號運算（包括積分和方程式）、任意精度計算、誤差傳播、區間算術以及友善的使用者介面。

<br/>
<br/>
<p align="center">
  <a href="fastlane/metadata/android/en-US/images/phoneScreenshots/1.png">
    <img src="fastlane/metadata/android/en-US/images/phoneScreenshots/1.png" width="20%">
  </a>
  <a href="fastlane/metadata/android/en-US/images/phoneScreenshots/2.png">
    <img src="fastlane/metadata/android/en-US/images/phoneScreenshots/2.png" width="20%">
  </a>
  <a href="fastlane/metadata/android/en-US/images/phoneScreenshots/3.png">
    <img src="fastlane/metadata/android/en-US/images/phoneScreenshots/3.png" width="20%">
  </a>
  <a href="fastlane/metadata/android/en-US/images/phoneScreenshots/4.png">
    <img src="fastlane/metadata/android/en-US/images/phoneScreenshots/4.png" width="20%">
  </a>
</p>

# 下載

請從 [發佈頁面 (Releases Section)](https://github.com/wade-fs/qalculator/releases/latest) 下載最新的 APK 檔案。

> [!WARNING]
> 本專案支持開放且獨立的 Android 生態系統，不會參與 Google 進一步鎖定 Android 的行動。因此，自 2026 年 9 月起，此應用程式可能無法在許多裝置上安裝。更多資訊：https://keepandroidopen.org/

# 編譯

```bash
git clone https://github.com/wade-fs/qalculator
cd qalculator
./gradlew assembleDebug
```

## 功能特色
針對 Android 版的特定功能：

* 平台原生圖形介面 (GUI)
* 簡潔的預設視圖
* 選配的「隨打即算」模式

源自 libqalculate 的核心功能：

* 運算與解析
* 結果顯示
* 符號運算
* 函數
* 單位
* 變數與常數
* 以及更多...

_關於語法以及可用函數、單位和變數的更多細節，請參閱 [說明手冊](https://qalculate.github.io/manual/)_。
