(ns triangulate.benchmark
  (:require [triangulate.core :refer [triangulate]])
  (:gen-class))


(def points-10 [[90.05 20.63]
                [42.68 84.58]
                [54.33 90.04]
                [44.18 73.74]
                [16.89 59.00]
                [92.67 46.99]
                [7.37 76.19]
                [91.44 82.61]
                [0.55 82.71]
                [48.90 53.56]])


(def points-1000 [[77.71 30.22]
                  [58.44 76.06]
                  [73.73 99.93]
                  [87.56 70.64]
                  [83.99 75.89]
                  [74.30 24.33]
                  [1.39 92.32]
                  [34.53 83.44]
                  [75.98 27.85]
                  [76.69 26.51]
                  [25.93 57.87]
                  [86.03 95.57]
                  [63.22 5.47]
                  [61.84 84.64]
                  [66.12 97.28]
                  [32.29 42.54]
                  [85.12 33.12]
                  [77.99 63.58]
                  [52.28 33.66]
                  [79.50 59.68]
                  [74.42 39.29]
                  [67.12 29.83]
                  [30.32 94.98]
                  [91.46 68.80]
                  [58.92 70.97]
                  [52.65 86.26]
                  [21.25 37.12]
                  [22.66 39.11]
                  [42.19 30.03]
                  [70.41 15.56]
                  [89.73 26.37]
                  [52.70 47.64]
                  [29.51 63.07]
                  [65.13 70.25]
                  [0.01 25.91]
                  [56.73 89.72]
                  [89.85 61.57]
                  [99.78 72.37]
                  [63.06 54.98]
                  [71.67 14.69]
                  [70.08 53.84]
                  [49.91 10.44]
                  [3.08 15.27]
                  [43.02 87.43]
                  [2.31 95.52]
                  [80.37 46.81]
                  [97.18 3.41]
                  [62.07 1.00]
                  [70.42 90.11]
                  [26.59 73.22]
                  [66.52 53.81]
                  [28.06 90.76]
                  [26.82 96.39]
                  [27.77 77.37]
                  [22.65 80.95]
                  [95.41 25.47]
                  [75.38 4.17]
                  [84.11 8.57]
                  [97.35 15.32]
                  [90.40 75.63]
                  [61.63 85.15]
                  [35.53 55.18]
                  [9.66 52.07]
                  [7.39 84.55]
                  [36.20 50.44]
                  [84.03 71.86]
                  [48.47 56.57]
                  [19.09 92.46]
                  [82.53 71.41]
                  [6.87 19.47]
                  [74.33 96.00]
                  [60.87 89.17]
                  [24.80 45.57]
                  [33.10 5.48]
                  [68.89 91.14]
                  [73.71 95.50]
                  [57.27 14.01]
                  [80.43 37.82]
                  [85.49 7.37]
                  [15.59 99.03]
                  [60.83 62.13]
                  [74.85 30.14]
                  [79.47 29.29]
                  [76.17 94.47]
                  [97.91 60.24]
                  [0.27 21.12]
                  [21.71 87.76]
                  [54.41 4.07]
                  [1.75 25.76]
                  [50.35 96.06]
                  [23.86 33.80]
                  [84.01 78.44]
                  [1.00 13.50]
                  [79.93 23.34]
                  [28.85 78.33]
                  [16.29 3.22]
                  [45.71 65.87]
                  [78.14 43.84]
                  [45.07 41.87]
                  [95.45 61.93]
                  [25.70 79.46]
                  [10.68 76.07]
                  [27.94 83.48]
                  [85.84 30.44]
                  [97.35 69.82]
                  [3.28 25.08]
                  [37.19 2.96]
                  [36.03 3.39]
                  [59.78 40.83]
                  [63.88 7.79]
                  [23.45 35.48]
                  [89.55 78.02]
                  [46.50 90.66]
                  [65.53 32.87]
                  [22.69 82.79]
                  [76.88 45.81]
                  [23.49 10.54]
                  [37.50 97.93]
                  [61.96 50.15]
                  [48.65 67.14]
                  [9.76 58.37]
                  [36.69 91.92]
                  [5.00 83.17]
                  [34.66 29.71]
                  [61.36 13.50]
                  [96.76 15.70]
                  [91.22 23.54]
                  [38.61 15.68]
                  [84.95 44.57]
                  [77.07 58.55]
                  [46.54 28.01]
                  [3.16 28.34]
                  [71.82 46.85]
                  [58.92 93.68]
                  [69.63 19.61]
                  [5.27 53.33]
                  [68.49 30.84]
                  [61.11 80.40]
                  [22.08 65.25]
                  [66.45 55.12]
                  [24.82 68.74]
                  [72.25 86.15]
                  [76.02 98.96]
                  [87.66 33.92]
                  [85.67 30.43]
                  [22.11 65.91]
                  [97.59 34.81]
                  [43.80 93.44]
                  [17.21 65.61]
                  [39.06 62.24]
                  [69.96 38.58]
                  [97.36 95.07]
                  [83.74 13.65]
                  [66.16 13.57]
                  [84.33 52.61]
                  [16.12 98.60]
                  [16.14 48.00]
                  [32.93 83.78]
                  [52.32 92.56]
                  [4.62 15.08]
                  [37.99 86.58]
                  [5.16 50.94]
                  [18.49 60.59]
                  [84.12 22.12]
                  [40.25 24.88]
                  [60.07 17.99]
                  [76.30 43.86]
                  [81.01 17.05]
                  [20.66 73.46]
                  [58.99 33.44]
                  [16.13 92.65]
                  [8.08 8.27]
                  [28.79 6.44]
                  [14.84 34.84]
                  [58.87 97.09]
                  [48.82 70.33]
                  [82.75 52.54]
                  [76.82 52.90]
                  [53.53 6.78]
                  [79.23 12.88]
                  [48.66 85.51]
                  [79.32 39.29]
                  [42.51 62.32]
                  [87.01 39.19]
                  [98.74 95.40]
                  [27.59 5.85]
                  [22.90 33.04]
                  [72.88 94.36]
                  [89.26 47.38]
                  [17.17 88.20]
                  [57.44 79.86]
                  [43.83 28.78]
                  [24.15 59.82]
                  [9.60 71.61]
                  [13.56 87.66]
                  [33.03 27.11]
                  [95.35 58.16]
                  [11.47 42.04]
                  [34.16 36.09]
                  [6.13 78.33]
                  [16.07 86.24]
                  [29.23 50.16]
                  [68.43 3.11]
                  [38.78 87.06]
                  [87.67 81.76]
                  [96.85 1.39]
                  [16.46 20.31]
                  [75.19 51.66]
                  [66.51 47.56]
                  [98.84 9.71]
                  [65.95 24.21]
                  [2.25 59.54]
                  [21.52 75.21]
                  [35.63 78.18]
                  [24.73 72.59]
                  [99.34 74.90]
                  [98.68 2.67]
                  [3.78 94.11]
                  [77.39 20.20]
                  [48.23 19.46]
                  [33.28 12.66]
                  [88.19 51.68]
                  [19.75 72.64]
                  [13.13 71.50]
                  [76.14 3.31]
                  [88.56 39.98]
                  [49.53 62.93]
                  [25.43 33.63]
                  [24.67 87.78]
                  [27.46 59.29]
                  [16.80 19.67]
                  [33.10 44.55]
                  [74.85 4.52]
                  [12.14 53.59]
                  [62.75 20.97]
                  [99.35 35.47]
                  [35.10 44.73]
                  [47.51 72.06]
                  [0.20 53.36]
                  [60.36 56.26]
                  [68.13 28.69]
                  [22.38 97.58]
                  [14.51 19.76]
                  [76.90 21.94]
                  [51.71 87.67]
                  [16.35 5.04]
                  [88.23 65.69]
                  [39.58 54.82]
                  [74.44 32.94]
                  [99.60 37.38]
                  [78.21 17.80]
                  [12.46 22.71]
                  [7.46 18.68]
                  [22.78 12.29]
                  [98.45 53.94]
                  [24.83 98.48]
                  [18.17 30.20]
                  [19.34 97.39]
                  [87.44 48.85]
                  [45.83 91.62]
                  [17.01 54.35]
                  [67.50 16.79]
                  [63.36 64.61]
                  [1.91 26.84]
                  [53.19 13.66]
                  [84.20 76.99]
                  [95.66 80.10]
                  [21.58 70.42]
                  [0.54 70.75]
                  [48.23 24.96]
                  [46.57 78.38]
                  [17.45 29.49]
                  [99.38 34.86]
                  [74.84 76.24]
                  [61.17 36.44]
                  [40.23 74.75]
                  [45.46 26.96]
                  [89.07 76.09]
                  [9.21 86.11]
                  [64.49 61.90]
                  [42.03 85.18]
                  [70.13 81.42]
                  [7.61 40.65]
                  [90.93 52.38]
                  [55.97 17.17]
                  [63.97 76.48]
                  [76.82 5.81]
                  [74.87 58.53]
                  [44.10 30.97]
                  [86.45 60.24]
                  [9.74 19.71]
                  [15.90 63.78]
                  [40.56 1.21]
                  [64.31 68.26]
                  [56.64 90.67]
                  [68.16 93.22]
                  [21.42 52.05]
                  [86.11 16.95]
                  [54.65 27.01]
                  [56.42 34.84]
                  [87.38 63.66]
                  [60.66 83.16]
                  [55.24 78.08]
                  [7.24 36.91]
                  [30.73 33.85]
                  [4.88 57.04]
                  [28.37 30.65]
                  [30.50 31.96]
                  [77.47 51.43]
                  [8.67 30.87]
                  [46.58 29.68]
                  [10.54 62.53]
                  [20.66 63.02]
                  [48.76 11.92]
                  [77.85 17.77]
                  [92.18 9.37]
                  [85.96 39.89]
                  [49.10 72.24]
                  [40.43 12.38]
                  [70.93 47.21]
                  [95.54 76.53]
                  [9.78 73.12]
                  [98.37 64.85]
                  [22.51 78.70]
                  [21.07 77.50]
                  [51.96 27.28]
                  [1.21 70.51]
                  [31.22 21.75]
                  [42.18 14.32]
                  [50.99 86.52]
                  [96.26 24.00]
                  [2.07 50.83]
                  [51.06 58.94]
                  [54.88 67.87]
                  [67.18 38.82]
                  [66.32 17.38]
                  [69.47 25.47]
                  [10.63 56.08]
                  [77.11 75.38]
                  [15.26 0.01]
                  [69.04 76.81]
                  [45.47 45.24]
                  [20.12 46.65]
                  [22.03 59.68]
                  [10.91 77.84]
                  [75.35 7.58]
                  [9.30 95.52]
                  [36.13 0.25]
                  [45.71 1.78]
                  [37.99 2.23]
                  [55.21 63.03]
                  [60.51 59.94]
                  [39.98 21.31]
                  [72.66 61.91]
                  [12.94 91.67]
                  [45.92 83.94]
                  [19.92 41.28]
                  [94.06 20.34]
                  [1.55 32.79]
                  [62.32 53.61]
                  [48.52 54.41]
                  [32.46 68.34]
                  [24.22 52.28]
                  [32.16 72.76]
                  [77.21 26.75]
                  [37.49 51.50]
                  [42.82 53.76]
                  [35.15 36.15]
                  [37.84 44.59]
                  [67.71 47.31]
                  [85.13 63.30]
                  [19.50 30.91]
                  [66.17 79.31]
                  [13.34 84.61]
                  [88.55 44.89]
                  [79.18 46.03]
                  [72.83 51.95]
                  [24.33 93.25]
                  [8.56 40.71]
                  [82.14 90.47]
                  [2.72 4.04]
                  [41.07 96.91]
                  [45.60 55.47]
                  [59.10 45.13]
                  [48.94 6.45]
                  [50.09 86.93]
                  [68.82 28.88]
                  [87.87 99.20]
                  [12.26 62.73]
                  [11.79 40.02]
                  [40.18 65.32]
                  [32.72 46.25]
                  [22.03 88.74]
                  [32.78 67.75]
                  [67.55 15.06]
                  [23.81 40.42]
                  [59.76 75.13]
                  [79.75 10.80]
                  [40.14 25.96]
                  [86.27 38.05]
                  [51.14 64.36]
                  [57.46 15.69]
                  [24.96 9.88]
                  [53.75 75.32]
                  [17.70 98.79]
                  [97.86 57.01]
                  [27.71 92.93]
                  [26.30 57.09]
                  [4.09 21.63]
                  [32.81 93.06]
                  [46.58 43.76]
                  [57.14 57.44]
                  [21.87 37.31]
                  [47.46 67.64]
                  [72.66 98.92]
                  [78.29 57.28]
                  [76.05 62.87]
                  [36.74 96.04]
                  [15.26 29.50]
                  [99.72 69.43]
                  [65.15 27.16]
                  [12.13 98.07]
                  [55.79 18.16]
                  [41.80 26.51]
                  [70.98 99.29]
                  [30.98 65.95]
                  [58.67 40.74]
                  [85.82 32.11]
                  [79.09 53.09]
                  [45.70 80.03]
                  [92.80 71.56]
                  [52.35 0.57]
                  [95.88 99.29]
                  [94.97 10.28]
                  [42.11 56.51]
                  [42.23 35.75]
                  [53.44 41.86]
                  [84.41 66.23]
                  [8.66 23.13]
                  [99.88 24.31]
                  [18.06 40.36]
                  [28.77 83.54]
                  [36.83 21.80]
                  [83.79 43.65]
                  [43.55 84.73]
                  [53.14 59.82]
                  [12.21 17.20]
                  [53.89 10.16]
                  [35.22 14.43]
                  [75.18 77.80]
                  [13.55 74.10]
                  [10.57 17.97]
                  [15.67 38.25]
                  [15.89 78.60]
                  [21.33 6.76]
                  [45.20 43.75]
                  [8.40 36.76]
                  [10.98 73.07]
                  [11.58 85.97]
                  [86.73 17.82]
                  [80.10 53.71]
                  [20.28 4.04]
                  [88.31 22.84]
                  [54.37 86.99]
                  [7.89 8.34]
                  [57.29 97.40]
                  [33.17 73.68]
                  [50.94 19.64]
                  [62.28 85.01]
                  [86.28 64.94]
                  [75.79 34.83]
                  [56.72 28.24]
                  [22.01 81.85]
                  [59.11 46.73]
                  [13.35 21.81]
                  [8.57 9.83]
                  [61.76 25.14]
                  [89.05 6.02]
                  [32.94 51.83]
                  [74.05 29.86]
                  [78.73 81.02]
                  [83.96 14.56]
                  [15.10 73.83]
                  [55.39 99.63]
                  [49.07 21.09]
                  [85.91 91.14]
                  [22.17 15.09]
                  [82.93 22.97]
                  [30.26 23.68]
                  [89.71 81.36]
                  [99.94 38.80]
                  [82.99 12.28]
                  [50.40 17.32]
                  [37.46 91.38]
                  [46.09 31.38]
                  [29.71 99.53]
                  [50.44 98.14]
                  [11.49 13.73]
                  [23.92 64.03]
                  [15.84 92.48]
                  [70.32 77.72]
                  [72.86 7.82]
                  [24.97 97.40]
                  [2.10 51.61]
                  [95.13 43.22]
                  [17.80 23.33]
                  [8.95 23.70]
                  [29.93 88.04]
                  [2.24 36.14]
                  [49.22 79.10]
                  [74.55 65.23]
                  [7.43 7.97]
                  [75.38 81.74]
                  [85.71 35.60]
                  [39.60 73.60]
                  [3.12 53.87]
                  [76.84 25.35]
                  [55.13 23.79]
                  [34.21 98.76]
                  [7.53 17.93]
                  [93.57 57.54]
                  [45.22 18.25]
                  [35.22 21.84]
                  [47.08 28.17]
                  [98.42 34.60]
                  [8.37 67.50]
                  [95.63 71.18]
                  [60.30 32.44]
                  [92.39 26.01]
                  [42.82 19.43]
                  [47.47 65.51]
                  [70.92 30.96]
                  [19.02 45.63]
                  [97.71 4.67]
                  [81.67 53.45]
                  [17.83 32.49]
                  [66.77 48.10]
                  [74.04 65.80]
                  [33.28 43.51]
                  [26.40 34.82]
                  [0.59 97.24]
                  [18.90 45.32]
                  [25.85 43.77]
                  [31.24 54.71]
                  [88.61 37.95]
                  [35.31 70.27]
                  [53.14 55.22]
                  [84.84 57.88]
                  [14.91 28.38]
                  [54.75 96.36]
                  [2.03 88.12]
                  [12.14 64.29]
                  [94.51 13.91]
                  [44.87 99.52]
                  [44.80 95.31]
                  [95.62 29.16]
                  [31.48 49.68]
                  [49.41 27.77]
                  [54.61 41.78]
                  [0.71 25.16]
                  [86.28 3.15]
                  [82.82 51.30]
                  [22.42 54.53]
                  [76.36 76.07]
                  [43.28 93.59]
                  [26.69 73.58]
                  [79.08 21.88]
                  [90.43 17.41]
                  [94.46 49.79]
                  [0.33 91.53]
                  [51.00 36.48]
                  [84.72 84.28]
                  [80.43 93.00]
                  [2.69 17.51]
                  [2.92 0.93]
                  [59.46 52.00]
                  [54.60 76.47]
                  [48.01 1.09]
                  [46.31 40.45]
                  [21.86 79.74]
                  [61.70 38.35]
                  [42.36 13.46]
                  [16.95 91.42]
                  [58.69 32.71]
                  [46.53 49.57]
                  [73.36 82.41]
                  [50.12 60.24]
                  [17.49 83.10]
                  [33.99 63.30]
                  [10.39 90.72]
                  [7.04 71.39]
                  [6.32 15.40]
                  [46.48 97.77]
                  [37.45 75.88]
                  [30.90 49.02]
                  [46.63 86.26]
                  [49.60 8.87]
                  [66.12 94.00]
                  [36.23 1.07]
                  [39.23 14.46]
                  [42.12 34.33]
                  [0.64 35.91]
                  [59.86 0.18]
                  [27.96 1.28]
                  [31.47 16.12]
                  [52.63 8.14]
                  [27.21 80.44]
                  [70.90 6.64]
                  [60.43 32.66]
                  [27.51 16.88]
                  [12.35 94.19]
                  [53.86 31.54]
                  [76.31 97.39]
                  [30.00 17.66]
                  [95.23 97.93]
                  [5.95 82.01]
                  [58.52 5.61]
                  [60.94 18.76]
                  [44.79 61.55]
                  [1.98 15.12]
                  [45.53 35.94]
                  [2.65 51.07]
                  [81.35 20.02]
                  [61.40 92.31]
                  [27.31 38.17]
                  [15.23 42.55]
                  [96.79 11.63]
                  [78.64 90.51]
                  [94.18 64.22]
                  [90.85 59.88]
                  [36.12 71.95]
                  [44.53 46.46]
                  [58.14 23.66]
                  [38.51 5.47]
                  [68.84 77.66]
                  [43.66 34.51]
                  [87.70 70.61]
                  [96.39 82.46]
                  [3.69 59.37]
                  [37.34 3.42]
                  [22.19 53.54]
                  [94.16 65.89]
                  [25.28 27.60]
                  [66.99 22.47]
                  [56.59 84.67]
                  [12.39 42.61]
                  [56.16 81.04]
                  [37.74 12.28]
                  [12.89 10.62]
                  [11.47 8.09]
                  [72.71 91.18]
                  [66.91 60.76]
                  [38.99 90.89]
                  [27.19 32.90]
                  [28.53 82.58]
                  [7.29 49.59]
                  [1.15 15.89]
                  [55.33 36.71]
                  [63.77 52.80]
                  [79.85 41.52]
                  [61.07 21.03]
                  [72.44 32.83]
                  [3.86 1.16]
                  [75.66 4.02]
                  [53.49 78.77]
                  [65.17 31.03]
                  [76.31 60.55]
                  [11.92 69.62]
                  [5.44 74.87]
                  [65.34 11.67]
                  [98.92 51.26]
                  [51.67 58.22]
                  [63.10 0.95]
                  [13.01 8.94]
                  [18.00 1.20]
                  [22.05 11.98]
                  [17.32 19.57]
                  [94.96 66.12]
                  [0.19 1.42]
                  [90.97 17.96]
                  [43.75 83.73]
                  [30.59 50.79]
                  [6.22 91.25]
                  [86.72 19.08]
                  [98.69 17.46]
                  [2.77 42.77]
                  [0.30 92.05]
                  [70.46 38.61]
                  [36.60 88.83]
                  [55.72 32.88]
                  [73.48 33.91]
                  [96.39 68.78]
                  [40.29 32.45]
                  [7.25 95.19]
                  [84.77 44.17]
                  [19.17 98.75]
                  [79.91 2.75]
                  [47.70 20.80]
                  [48.12 92.20]
                  [33.58 82.37]
                  [41.53 64.85]
                  [22.67 25.97]
                  [19.24 9.28]
                  [55.99 71.17]
                  [91.65 54.66]
                  [21.98 72.51]
                  [60.43 28.00]
                  [82.56 62.90]
                  [68.27 64.77]
                  [22.03 63.37]
                  [87.04 10.12]
                  [83.85 41.06]
                  [14.96 37.81]
                  [53.84 1.18]
                  [24.21 57.11]
                  [20.64 5.80]
                  [98.51 74.01]
                  [94.09 22.83]
                  [27.14 22.55]
                  [90.68 21.53]
                  [76.55 84.39]
                  [1.30 47.79]
                  [43.66 70.39]
                  [41.20 31.74]
                  [5.94 46.49]
                  [44.59 3.20]
                  [22.50 48.40]
                  [6.93 16.30]
                  [7.95 43.98]
                  [97.12 13.53]
                  [95.70 70.81]
                  [58.30 91.73]
                  [31.43 97.14]
                  [91.58 67.75]
                  [88.87 29.32]
                  [69.78 79.75]
                  [79.69 15.48]
                  [59.97 1.91]
                  [37.62 76.64]
                  [42.90 94.56]
                  [92.40 29.41]
                  [4.91 59.94]
                  [13.85 51.69]
                  [43.93 24.20]
                  [98.32 82.54]
                  [74.76 20.69]
                  [90.22 40.57]
                  [40.80 6.43]
                  [80.00 91.16]
                  [39.84 3.98]
                  [96.50 92.51]
                  [49.13 24.44]
                  [16.37 2.59]
                  [92.01 93.58]
                  [46.24 34.86]
                  [22.16 72.89]
                  [94.31 9.17]
                  [14.80 40.11]
                  [37.21 56.32]
                  [44.23 69.54]
                  [5.18 99.26]
                  [96.51 31.59]
                  [54.26 52.52]
                  [12.69 13.02]
                  [67.33 52.14]
                  [83.32 44.68]
                  [28.01 53.11]
                  [17.96 11.94]
                  [40.61 8.39]
                  [94.99 14.34]
                  [27.92 74.50]
                  [56.46 35.91]
                  [23.91 72.19]
                  [50.04 85.01]
                  [93.29 67.33]
                  [36.30 63.77]
                  [51.67 27.57]
                  [73.01 30.96]
                  [30.61 52.92]
                  [60.35 29.37]
                  [30.54 74.85]
                  [80.88 58.95]
                  [30.91 67.77]
                  [96.54 90.47]
                  [81.99 19.17]
                  [70.95 30.84]
                  [85.47 60.25]
                  [70.19 70.91]
                  [82.07 67.66]
                  [57.45 69.28]
                  [3.62 8.62]
                  [37.95 51.67]
                  [14.24 16.86]
                  [29.17 34.16]
                  [60.84 54.58]
                  [28.46 11.02]
                  [76.30 37.15]
                  [25.39 82.85]
                  [31.00 71.40]
                  [59.06 39.30]
                  [7.43 85.68]
                  [52.27 52.10]
                  [81.05 10.84]
                  [63.72 78.66]
                  [26.42 4.50]
                  [87.09 24.44]
                  [31.69 98.00]
                  [91.64 63.04]
                  [97.01 85.62]
                  [42.55 66.95]
                  [12.67 68.13]
                  [61.57 59.07]
                  [61.42 9.52]
                  [88.37 55.45]
                  [66.04 64.73]
                  [81.29 32.92]
                  [8.03 88.74]
                  [52.54 21.82]
                  [76.08 42.93]
                  [67.37 93.82]
                  [64.33 73.35]
                  [49.37 87.37]
                  [29.19 47.32]
                  [24.67 89.33]
                  [31.03 99.73]
                  [6.73 70.28]
                  [32.48 58.45]
                  [70.52 53.11]
                  [98.39 89.50]
                  [89.62 79.88]
                  [44.44 27.00]
                  [13.36 30.17]
                  [10.90 30.07]
                  [42.43 35.65]
                  [96.24 39.92]
                  [20.42 30.37]
                  [71.07 93.54]
                  [97.14 73.91]
                  [7.16 87.55]
                  [34.44 85.32]
                  [98.63 75.64]
                  [19.71 30.09]
                  [22.71 25.94]
                  [24.03 9.12]
                  [85.99 61.19]
                  [91.84 9.32]
                  [28.93 53.44]
                  [77.54 88.41]
                  [15.94 47.91]
                  [50.59 83.48]
                  [21.36 45.82]
                  [75.12 52.04]
                  [32.79 85.04]
                  [32.87 77.38]
                  [32.21 18.88]
                  [98.75 95.76]
                  [82.76 8.59]
                  [97.26 32.05]
                  [44.33 12.93]
                  [65.12 62.39]
                  [64.80 79.69]
                  [20.29 58.06]
                  [45.30 17.98]
                  [90.96 2.70]
                  [57.69 57.82]
                  [89.53 16.74]
                  [0.39 84.09]
                  [25.67 67.64]
                  [14.65 65.46]
                  [40.74 92.07]
                  [47.72 85.84]
                  [11.02 7.05]
                  [41.25 25.87]
                  [11.33 41.39]
                  [44.25 34.99]
                  [52.69 75.41]
                  [97.80 80.60]
                  [7.37 37.58]
                  [16.03 60.75]
                  [13.38 69.95]
                  [92.12 18.37]
                  [6.47 40.66]
                  [91.31 1.52]
                  [62.26 51.57]
                  [53.86 72.01]
                  [43.80 61.84]
                  [19.97 77.34]
                  [40.54 23.89]
                  [6.17 65.03]
                  [53.41 7.68]
                  [7.78 37.28]
                  [27.97 70.80]
                  [75.93 14.49]
                  [62.30 17.48]
                  [19.04 0.75]
                  [19.95 78.06]
                  [8.47 21.88]
                  [59.27 31.57]
                  [26.14 37.00]
                  [50.05 89.17]
                  [77.74 8.48]
                  [86.07 53.60]
                  [9.42 51.85]
                  [17.73 70.93]
                  [8.12 77.06]
                  [22.91 94.59]
                  [36.08 75.94]
                  [6.04 89.21]
                  [48.68 6.36]
                  [65.69 11.63]
                  [71.35 73.42]
                  [98.93 20.11]
                  [66.04 10.10]
                  [17.23 79.19]
                  [0.78 15.44]
                  [33.17 47.12]
                  [46.80 17.20]
                  [39.69 55.91]
                  [66.83 87.43]
                  [44.42 19.47]
                  [25.57 54.68]
                  [40.54 34.70]
                  [48.57 24.37]
                  [92.35 72.25]
                  [79.60 40.10]
                  [89.25 89.12]
                  [37.77 93.23]
                  [74.50 33.51]
                  [56.03 14.99]
                  [87.06 53.89]
                  [90.04 66.61]
                  [63.76 68.90]
                  [22.80 76.01]
                  [37.05 2.42]
                  [54.49 56.85]
                  [29.06 39.06]
                  [59.15 85.51]
                  [86.69 9.25]
                  [10.43 4.62]
                  [24.20 70.35]
                  [26.92 68.02]
                  [40.32 10.35]
                  [64.61 41.27]
                  [54.47 64.15]
                  [88.36 24.40]
                  [63.70 99.02]
                  [87.54 68.94]
                  [55.93 95.17]
                  [61.81 20.26]
                  [27.90 20.51]
                  [92.38 27.70]
                  [47.01 74.22]
                  [31.00 30.31]
                  [64.81 52.99]
                  [84.95 43.75]
                  [37.26 75.75]
                  [49.08 45.25]
                  [31.58 88.02]
                  [38.34 77.90]
                  [3.04 55.56]
                  [3.45 72.81]
                  [6.20 48.14]
                  [70.65 18.43]
                  [64.77 9.17]
                  [32.32 89.76]
                  [61.49 4.73]
                  [29.40 87.48]
                  [25.75 46.05]
                  [9.06 26.23]
                  [89.80 83.90]
                  [11.07 55.13]
                  [57.07 80.31]
                  [35.34 89.26]
                  [75.66 73.21]
                  [69.32 80.28]
                  [38.56 63.10]
                  [9.07 57.07]
                  [38.63 67.34]
                  [90.46 22.43]
                  [90.38 7.16]
                  [58.22 0.76]
                  [93.71 15.43]
                  [39.55 93.68]
                  [55.19 78.93]
                  [56.38 14.97]
                  [1.36 58.00]
                  [5.75 44.84]
                  [15.54 58.51]
                  [61.28 64.31]
                  [4.72 66.37]
                  [0.47 5.00]
                  [56.31 93.07]
                  [42.16 77.08]
                  [75.94 22.53]
                  [86.69 18.55]
                  [6.86 93.81]
                  [86.95 42.91]
                  [19.19 2.21]
                  [43.08 48.89]
                  [22.89 52.15]])


(defn -main
  "Run the benchmarks"
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  (prn (triangulate points)))
