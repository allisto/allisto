from model import Allisto, AllistoPredict
from audio_analyzer import AudioAnalyze
import numpy as np

analyzed_audio = AudioAnalyze("sample.wav")
print("Audio Analysis Complete..")

allisto_prediction = AllistoPredict()
allisto_prediction.predict(analyzed_audio.slice_audio_parameters())

print(allisto_prediction)
