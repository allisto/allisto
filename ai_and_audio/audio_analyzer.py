from pyAudioAnalysis import audioBasicIO
from pyAudioAnalysis import audioFeatureExtraction
import numpy as np


class AudioAnalyze:

    def __init__(self, filename):
        [Fs, x] = audioBasicIO.readAudioFile(filename)
        F = audioFeatureExtraction.stFeatureExtraction(
            np.mean(x, axis=1), Fs, 0.050*Fs, 0.025*Fs)
        self.input_from_audio = F[0][1]

    def slice_audio_parameters(self):
        value = self.input_from_audio[:19]
        return value


"""
obj = AudioAnalyze("sample.wav")
value = obj.slice_audio_parameters()
"""
