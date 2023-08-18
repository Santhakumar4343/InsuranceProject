import React, { useState, useEffect } from 'react';
import axios from 'axios';

const SearchFunction = () => {
  const [data, setData] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const [planNames, setPlanNames] = useState([]);
  const [planStatuses, setPlanStatuses] = useState([]);
  const [selectedPlanName, setSelectedPlanName] = useState('');
  const [selectedPlanStatus, setSelectedPlanStatus] = useState('');
  const [selectedPlanGender, setSelectedPlanGender] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [filteredData, setFilteredData] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);
  const handleEXcelDownload = () => {
    window.location.href = 'http://localhost:8080/plans/excel';
  };
  const handlePdfDownload = () => {
    window.location.href = 'http://localhost:8080/plans/pdf';
  };

  const fetchData = async () => {
    try {
      const planNamesResponse = await axios.get("http://localhost:8080/plans/getPlanNames");
      setPlanNames(planNamesResponse.data);

      const planStatusesResponse = await axios.get("http://localhost:8080/plans/getPlanStatus");
      setPlanStatuses(planStatusesResponse.data);

      const response = await axios.get("http://localhost:8080/plans/getAll");
      setData(response.data);
      setIsLoading(false);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const filterData = () => {
    return data.filter(item => {
      const planNameMatch = selectedPlanName === '' || item.planName === selectedPlanName;
      const planStatusMatch = selectedPlanStatus === '' || item.planStatus === selectedPlanStatus;
      const planGenderMatch = selectedPlanGender === '' || item.gender === selectedPlanGender;
      const startDateMatch = startDate === '' || item.startDate === startDate;
      const endDateMatch = endDate === '' || item.endDate === endDate;

      return planNameMatch && planStatusMatch && planGenderMatch && startDateMatch && endDateMatch;
    });
  };

  const applyFilters = () => {
    const filteredItems = filterData();
    setFilteredData(filteredItems);
  };

  return (
    <>
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginTop: '50px' }}>
        <table>
          <tbody>
            <tr>
              <td>PlanName:</td>
              <td>
                <select value={selectedPlanName} onChange={(e) => setSelectedPlanName(e.target.value)}>
                  <option value="">Select a Plan Name</option>
                  {planNames.map((planName, index) => (
                    <option key={index} value={planName}>
                      {planName}
                    </option>
                  ))}
                </select>
              </td>
              <td>PlanStatus:</td>
              <td>
                <select value={selectedPlanStatus} onChange={(e) => setSelectedPlanStatus(e.target.value)}>
                  <option value="">Select a Plan Status</option>
                  {planStatuses.map((planStatus, index) => (
                    <option key={index} value={planStatus}>
                      {planStatus}
                    </option>
                  ))}
                </select>
              </td>
              <td>Plan Gender:</td>
              <td>
                <select value={selectedPlanGender} onChange={(e) => setSelectedPlanGender(e.target.value)}>
                  <option value="">Select a Gender</option>
                  <option value="Male">Male</option>
                  <option value="Fe-male">Female</option>
                </select>
              </td>
            </tr>
            <tr>
              <td>Start Date</td>
              <td><input type='date' name='startDate' value={startDate} onChange={(e) => setStartDate(e.target.value)} /></td>
              <td>End Date</td>
              <td><input type='date' name='endDate' value={endDate} onChange={(e) => setEndDate(e.target.value)} /></td>
              <td colSpan="2" style={{ textAlign: 'center' }}>
                <button onClick={applyFilters}>Search</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      {selectedPlanName === '' && selectedPlanStatus === '' && selectedPlanGender === '' && startDate === '' && endDate === '' ? (
        <p style={{ textAlign: 'center' }}>Select filters and click "Search" to see data.</p>
      ) : (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginTop: '20px' }}>
          {isLoading ? (
            <p>Loading...</p>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>Plan Name</th>
                  <th>Plan Status</th>
                  <th>Gender</th>
                  <th>Start Date</th>
                  <th>End Date</th>
                </tr>
              </thead>
              <tbody>
                {filteredData.map((item, index) => (
                  <tr key={index}>
                    <td>{item.planName}</td>
                    <td>{item.planStatus}</td>
                    <td>{item.gender}</td>
                    <td>{item.startDate}</td>
                    <td>{item.endDate}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
    <button onClick={handleEXcelDownload}>Download Excel</button>
    <button onClick={handlePdfDownload}>Download Pdf</button>
        </div>
      )}
    </>
  );
};

export default SearchFunction;
