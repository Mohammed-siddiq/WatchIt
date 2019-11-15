#you need to import google-cloud-automl to run the following code

#in terminal, before running the .py file, run the following:
#export GOOGLE_APPLICATION_CREDENTIALS="CREDENTIALS.json FILE"

#once you are done running the above, the format for running the file is as follows:
#python call_api.py content, project_id, model_id
#for us, it will be the below:
#python call_api.py IMAGE_FILE_PATH 946558125160 ICN6835430693418303488

import os
import sys
from google.cloud import automl_v1beta1 as automl
from google.cloud.automl_v1beta1.proto import service_pb2

os.system('clear') #clears the console before running the below

# 'content' is base-64-encoded image data.
def get_prediction(content, project_id, model_id):
  prediction_client = automl.PredictionServiceClient()

  name = 'projects/{}/locations/us-central1/models/{}'.format(project_id, model_id)
  payload = {'image': {'image_bytes': content }}
  params = {}
  request = prediction_client.predict(name, payload, params)
  return request  # waits till request is returned

if __name__ == '__main__':
  file_path = sys.argv[1]
  project_id = sys.argv[2]
  model_id = sys.argv[3]

  with open(file_path, 'rb') as ff:
    content = ff.read()

  print(get_prediction(content, project_id, model_id))
