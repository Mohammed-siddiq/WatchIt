#need to run the below line in terminal so that we get google application credentials
#export GOOGLE_APPLICATION_CREDENTIALS="594-a37a08c9cbe2.json 

import os
import sys
from google.cloud import automl_v1beta1 as automl
from google.cloud.automl_v1beta1.proto import service_pb2

os.system('clear')
print("Console cleared.")

os.system('export GOOGLE_APPLICATION_CREDENTIALS="594-a37a08c9cbe2.json"')

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