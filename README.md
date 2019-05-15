# Overview
# Visual Search Android Application using Transfer Learning

Problem statement:
  Searching by either typing or voice is time consuming and hard task since WWW has vast information.Motivated by this, project on visual Search and Shop was done to minimize the difficulties. It is a Visual Search android application using transfer learning.   


Procedure used for the project:
-> MobileNet V2 pretrained model on ImageNet was used to retrain the model on cosmetics dataset using python in TensorFlow.
-> The model was compressed into "graph.lite" and the labels as "labels.txt" which is stored in "assets" folder in Android Studio.
-> The source code for Android applicaton is found in this repository.
