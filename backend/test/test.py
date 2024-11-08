import requests

url = "http://108.30.159.119:8443"


def testCreateClass():
    class_id = "12345"
    class_name = "Test Class 2"
    class_description = "This is a test class"
    teacher_id = "91234790-8f47-11e9-bc42-526af7764f64"  # Use the known UUID

    endpoint = url + "/api/make-class"
    body = {
        "google-classroom-id": class_id,
        "class-name": class_name,
        "class-description": class_description,
        "teacher-id": teacher_id,
    }
    response = requests.post(endpoint, json=body)
    print(response.json())
    assert response.status_code == 200


def testGetClasses():
    teacher_id = "91234790-8f47-11e9-bc42-526af7764f64"  # Use the known UUID
    body = {"teacher-id": teacher_id}
    endpoint = url + "/api/get-classes"
    response = requests.post(endpoint, json=body)
    print(response.json())


def testCreateMC():
    endpoint = url + "/create-mc"
    body = {
        "sa-topic": "France",
    }
    response = requests.post(endpoint, json=body)
    print(response)


testGetClasses()
# testCreateClass()
# testCreateMC()
