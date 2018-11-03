from pyAudioAnalysis import audioBasicIO
from pyAudioAnalysis import audioFeatureExtraction
import numpy as np

# Reading the audio file
[Fs, x] = audioBasicIO.readAudioFile("sample.wav")
F = audioFeatureExtraction.stFeatureExtraction(np.mean(x, axis=1),Fs, 0.050*Fs, 0.025*Fs)