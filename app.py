import sys
import os
path_to_app = os.path.join(os.path.realpath(os.path.dirname(__file__)), 'REST-API', 'Python')
sys.path.extend([path_to_app])
from quantisk_api import app as application

ip = os.environ.get('OPENSHIFT_PYTHON_IP', '0.0.0.0') 
port = int(os.environ.get('OPENSHIFT_PYTHON_PORT', 5000)) 

if __name__ == '__main__': 
    application.run(debug=True, host=ip, port=port)