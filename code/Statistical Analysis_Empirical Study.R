data = read.csv("C:/Users/jagsi/Documents/Virginia Tech/Software Engineering/Project/Observed_Predicted Values.csv", header = TRUE)
observed <- data[1]
simulated <- data[2]
#Root Mean Squared Error
library(hydroGOF)
error <- rmse(simulated, observed, na.rm = TRUE)
error  #0.9867711 on a scale of 1 to 5

#Normalized Root Mean Squared Error
nerror <- nrmse(simulated, observed, na.rm = TRUE, norm = "sd")
nerror #66.2% if based on sd
nerror_mm <- nrmse(simulated, observed, na.rm = TRUE, norm = "maxmin")
nerror_mm #24.7% if based on mm 

#Mean absolute error
diff <- simulated - observed
mae <- colMeans(diff, na.rm = TRUE)
mae #0.4730914

